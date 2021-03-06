/*
* (c) 2016 h. jakubzik, heiko.jakubzik@as.uni-heidelberg.de
*
* Skript für die Visualisierung von Studiengängen
*
*/
var DEGREE_KIT = (function ($) {
  var MODULE_NAME='DEGREE_KIT';
  var my={};

        // @TODO
        // (1) Modal bei "Leistungen und LP" muss 
        //     Liste der Leistungen anzeigen und 
        //     die Möglichkeit bieten, einzelne
        //     Leistungen zu entfernen........................OK
        //
        // (2) Es muss eine Schaltfläche geben, 
        //     die es erlaubt, aus der momentan 
        //     angezeigten Struktur einen neuen 
        //     Studiengang zu designen.
        //     
        //     Wegen (6) wäre es vielleicht nicht schlecht,
        //     in einem Zwischenformat speichern zu können.
        //     (Oder wir nutzen dann Bigapple ... ?)
        //
        // (3) Care und Erweiterungsfach auf diese 
        //     Weise erstellen und per Screenshot oder 
        //     so an MMI/NN
        //
        // (4) Auswahl anderer Studiengänge als GymPO
        //     zulassen.
        //
        // (6) Kommentieren von Designs zulassen
        
        // Sollen die Module beim Hinzufügen aufs Panel
        // auch in der Auswahlliste noch erhalten bleiben?
    my.init = function(){
        g_Duplicate_Modules=false;
        g_iFachID_Basis=-1;
        
        var g_sDescription='';
        $.signUpGlobal.seminar_id=1;
        
        $('#cmdCreateTxt').on('click', exportAsTxt);
        $('#cmdAddAllModules').on('click', addAllModules);
        $('#cmdDropAllModules').on('click', dropAllModules);
        $('#cmdCollapseAllModules').on('click', collapseAllModules);
        $('#cmdExpandAllModules').on('click', expandAllModules);
        $('#cmdSaveDegreeProgram').on('click', saveAsNewDegreeProgram);
        $('#cmdSaveOnServer').on('click', saveOnServer);
        $('#cboFach').on('change', function(){populateModule($(this).val());});
        $('#cmdRTF').on('click', function(){getRTF();});
        $('#myModalLabel').on('click', function(){
            if($('#myModalLabel input').length) return;
            
            // Zeige Input zum Edieren an:
            var sHTML = "<input type='text' class='form-control' id='txtNameModulNeu' value='" + $('#myModalLabel').text() +"'/>"
            $('#myModalLabel').html(sHTML);
            $('#txtNameModulNeu').on('blur', function(){
               $('#myModalLabel').html($(this).val());
                // Die neue Summe der LP des Moduls wird
                // in die Titelzeile geschrieben
                var oModul = $('#myModalLabel').data('shj_item');
                $('#modul_' + oModul.id).find('.template_modulname').text($(this).val());

                // Farbe auf blau
                // als Zeichen der Änderung
                $('#modul_' + oModul.id).removeClass("panel-default panel-success panel-danger panel-warning").addClass("panel-info");

                oModul.name=$(this).val().trim();
            });
        });
        $('#txtModulLP').on('click', function(){
            if($('#txtModulLP input').length) return;
            
            // Zeige Input zum Edieren an:
            var sHTML = "<div class='col-xs-2'><input type='text' class='form-control' id='txtLPModulNeu' value='" + $('#txtModulLP').text() +"'/></div>"
            $('#txtModulLP').html(sHTML);
            $('#txtLPModulNeu').on('blur', function(){
               $('#txtModulLP').html($(this).val());
            });
        });
        
        $.signUpGlobal.info.seminar.faecher = new shj.signUp.seminar.Faecher($.signUpGlobal.seminar_id,
            function(data){
                for (var ii=0,len=$.signUpGlobal.info.seminar.faecher.faecher.length;ii<len;ii++){
                    $('#cboFach').append('<option value="' + $.signUpGlobal.info.seminar.faecher.faecher[ii].id + '">' + $.signUpGlobal.info.seminar.faecher.faecher[ii].name + '</option>');
                }
                //$('#tModule').append(aTable.join(''));
//                $( ".draggable" ).draggable({ appendTo: "body", helper: "clone", revert:'invalid' });
                $( ".droppable_modules" ).droppable({
                    accept:".shjModul",
                    hoverClass: "shj_hover",
                    drop: function( event, ui ) {
                        // Baue Modul zusammen:
                        // -----------------------------
                        // Modulname: n+m+ ... + x LP  |
                        // - Leistung 1 (n LP)         |
                        // - Leistung 2 (m LP)         |
                        // ...                         |
                        // - Leistung z (x LP)         |
                        // -----------------------------
                        addModuleToPanel($(this),$(ui.draggable).data('shj_item'));
                                                
//                        $('.shjLeistungsContainer').droppable({
//                            accept: ".shj_leistung",
//                            hoverClass: "shj_hover",
//                            drop: function( event, ui ) {
//                                // Funktion schreiben:
//                                addLeistungToModul($(this).parent('div').parent('div').data('shj_item'), $(ui.draggable).data('shj_item'))
//                            }
//                        });
                    }
                  });
                  
                  initLeistungen();
        });
    }// End Init.
    //
        //
        var g_LPAktuell=parseInt(0);
        function addLP(fCP){
            if(isNaN(fCP)) return;
            g_LPAktuell+=parseFloat(fCP);
            refreshLP();
        };
        function subtractLP(fCP){
            if(isNaN(fCP)) return;
            g_LPAktuell-=fCP;
            refreshLP();
        };
        function refreshLP(){
            $('#txtLPAktuell').text(g_LPAktuell);
        };
        
        function saveAsNewDegreeProgram(){
            // 1. Schritt: Name und Beschreibung abfragen
            $('#modStudiengangNeu').modal('show');
        }
        function saveOnServer(){
            // 2. Schritt: Information über Studiengang für Server aufbereiten:
            // 
            // (a) Neuen Studiengang anlegen
            // 
            // (b) String erzeugen, der die Modularisierung beschreibt:
            //     
            //     b1 bereits vorhandenes Modul
            //     ID=2377
            //     
            //     b2 neues Modul
            //     Name=Modulname, LP=A
            //     - ID=Leistung1, LP=X
            //     - ID=Leistung2, LP=Y
            //
            
            // 2a Finde Module auf dem Panel
            var sModularisierung='';
            for (var ii=0,len=$.signUpGlobal.info.seminar.faecher.faecher.length;ii<len;ii++){
                if($.signUpGlobal.info.seminar.faecher.faecher[ii].id==g_iFachID_Basis){
                    for(jj=0;jj<$.signUpGlobal.info.seminar.faecher.faecher[ii].module.length;jj++){
                        if($('#' + $.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul.id).length>0){
                            // Modul befindet sich auf dem Panel
                            if($.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul.isDirty===true){
                                sModularisierung += "\nName=" + $.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul.name + ","
                                                    + "LP=" + $.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].cp;
                                for(kk=0;kk<$.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul.leistungen.length;kk++){
                                    sModularisierung+= "\n- ID=" + $.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul.leistungen[kk].leistung.id + ","
                                                + "LP=" + $.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul.leistungen[kk].leistung.cp;
                                }
                            }else{
                                sModularisierung += "\nID=" + $.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul.id;
                            }                            
                        }
                    }
                }
            }
            alert(sModularisierung);
            
            var studiengang=new shj.signUp.seminar.Fach();
            studiengang.namespace='seminar';
            studiengang.lifecycle_base_url="fach";
            studiengang.studiengang_name=$('#txtStudiengangName').val();
            studiengang.studiengang_version=$('#txtStudiengangVariante').val();
            studiengang.studiengang_beschreibung=$('#txtStudiengangBeschreibung').val();
            studiengang.studiengang_cp=4711;
            studiengang.studiengang_aufbau=sModularisierung;

            studiengang.add(function(){alert('Added');});
        }
        
        function showEdiereModul(oModul){
            $('#myModalLabel').data('shj_item', oModul);
            $('#myModalLabel').text(oModul.name);
            $('#txtModulGewichtung').text(oModul.gewichtung);
            $('#txtModulLP').text(oModul.min_ects);
            var template=$('<tr><td class="li_leistung_name" width="80%"></td>' + 
                    '<td class="li_leistung_lp" width="10%"></td>' + 
                    '<td class="li_leistung_drop" width="10%">' + 
                    '<button class="btn btn-danger btn-xs">drop</button></td></tr>');
            
            var list=$('<table/>');
            
            // @ TODO (1)
            // .modal-body
            for(var ii=0; ii<oModul.leistungen.length;ii++){
                var tmp = template.clone();
                tmp.find('.li_leistung_name').text(oModul.leistungen[ii].leistung.name);
                tmp.find('.li_leistung_lp').text(' ' + oModul.leistungen[ii].leistung.cp + ' LP');
                tmp.find('button')
                        .data('shj_modul', oModul)
                        .data('shj_leistung', oModul.leistungen[ii].leistung)
                        .on('click', function(){dropLeistungFromModul($(this).data('shj_modul'),$(this).data('shj_leistung'));$('#modEdiereModul').modal('hide');});
                list.append(tmp);
            }
            $('#modEdiereModul .modal-body').html(list);
            $('#modEdiereModul').modal('show');
            // oModul.leistungen[ii].leistung.name, oModul.leistungen[ii].leistung.cp, act
            
        };

        // Leistungen initialisieren: sie müssen aus den Modulen
        // der Fächer ausgelesen werden -- das Stückchen Code
        // gibt es ja bestimmt schon irgendwo?
        function initLeistungen(){
            $.signUpGlobal.info.seminar.leistungen = $.signUpGlobal.info.seminar.faecher.getLeistungen();
            var lst=[];
            var template=$('<tr><td><span class="draggable shj_leistung"></span></td></tr>');
            for(var ii=0,len=$.signUpGlobal.info.seminar.leistungen.length; ii<len; ii++){
                // mit template umsetzen und .data übergeben!
                tpl=template.clone();
                tpl.find('.draggable').text($.signUpGlobal.info.seminar.leistungen[ii].name).data('shj_item',$.signUpGlobal.info.seminar.leistungen[ii]);
                $('#tLeistungen').append(tpl);
            }
            $( "#tLeistungen .draggable" ).draggable({ appendTo: "body", helper: "clone", revert:'invalid' });
        }
        
        function collapseAllModules(){
            $('.shj_mass-collapsible').collapse('hide');
        }
        function expandAllModules(){
            $('.shj_mass-collapsible').collapse('show');
        }
        function dropModul(oModul){
            if($('#' + oModul.id)<=0) return;
            g_sDescription += '\\par - entferne Modul ' + oModul.name + '\n';
            subtractLP(getCurrentLP(oModul.id));
            
            $('#' + oModul.id).remove();
            if(!g_Duplicate_Modules){
                var tmp=$('<tr><td><span class="draggable shjModul"></span></td></tr>').clone();
                tmp.find('.draggable').text(oModul.name).data('shj_item', oModul);
                $('#tModule').prepend(tmp);
            }
        }
        
        function dropAllModules(){
            g_sDescription ='';
            for (var ii=0,len=$.signUpGlobal.info.seminar.faecher.faecher.length;ii<len;ii++){
                if($.signUpGlobal.info.seminar.faecher.faecher[ii].id==g_iFachID_Basis){
                    for(jj=0;jj<$.signUpGlobal.info.seminar.faecher.faecher[ii].module.length;jj++){
                        if($('#' + $.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul.id).length>0) dropModul($.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul);
                    }
                }
            }
        }
        /**
         * Liefert die Studiengänge als 
         * Textdatei im Textformat, das mit 
         * util/
         * kompatibel ist
         * @returns {undefined}
         */
        function exportAsTxt(){
            var sText='';
            for (var ii=0,len=$.signUpGlobal.info.seminar.faecher.faecher.length;ii<len;ii++){
                if($.signUpGlobal.info.seminar.faecher.faecher[ii].id==g_iFachID_Basis){
                    sText='Studiengang=' + $.signUpGlobal.info.seminar.faecher.faecher[ii].name 
                        + ';Pruefung_Name=; Pruefung_EN_Name=;Pruefung_Abschluss=<Bachelor|Staatsexamen|Master>'
                        + ';Pruefung_ist_Abschluss=<true|false>;Pruefung_im_Hauptfach=<true|false>'
                        + ';Pruefung_LP=;Seminar_ID=;Pruefung_ID='
                        + ';EN=; ID=; REM=PO \n\n';
                console.log($.signUpGlobal.info.seminar.faecher.faecher[ii]);
                    for(jj=0;jj<$.signUpGlobal.info.seminar.faecher.faecher[ii].module.length;jj++){
                        if($('#modul_' + $.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul.id).length>0){
                            var mod=$.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul;
                            sText+='\n\n' + mod.name 
                                    + '; ID=' + mod.id
                                    + '; LP=' + mod.min_ects 
                                    + '; Gewichtung=' + mod.gewichtung + '\n';
                            
                            for(var kk=0, ll=mod.leistungen.length;kk<ll;kk++){
                                sText+= '- ' + mod.leistungen[kk].leistung.name 
                                        + '; ID=' + mod.leistungen[kk].leistung.id 
                                        + '; LP=' + mod.leistungen[kk].leistung.cp + '\n';
                            }
                            
                        }
                    }
                }
            }
//            window.open(sText);
            download('studiengang.txt', sText);
        }
        
        /**
         * Downloads a text-file with the name >filename< and 
         * contents >text<
         * @param {type} filename
         * @param {type} text
         * @returns {undefined}
         */
        function download(filename, text) {
            var element = document.createElement('a');
            element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
            element.setAttribute('download', filename);
            element.style.display = 'none';
            document.body.appendChild(element);
            element.click();
            document.body.removeChild(element);
        }
        
        // Füge alle Module dem Panel hinzu
        function addAllModules(){
            var iPlay=0;
            
            for (var ii=0,len=$.signUpGlobal.info.seminar.faecher.faecher.length;ii<len;ii++){
                if($.signUpGlobal.info.seminar.faecher.faecher[ii].id==g_iFachID_Basis){
                    g_sDescription = 'Ausgehend von: ' + $.signUpGlobal.info.seminar.faecher.faecher[ii].name + '\n\\par\n';
                    for(jj=0;jj<$.signUpGlobal.info.seminar.faecher.faecher[ii].module.length;jj++){
                        if($('#modul_' + $.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul.id).length<=0){
                            if(iPlay===0) oDOMVar=$('#playground_1');
                            else if(iPlay===1) oDOMVar=$('#playground_2');
                            else if(iPlay===2) oDOMVar=$('#playground_3');
                            iPlay++;
                            addModuleToPanel(oDOMVar, $.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul);
                            if(iPlay>=3) iPlay=0;
                        }
                    }
                }
            }
        }
        
        // Stellt das Modul auf dem übergebenen 
        // Panel dar
        function addModuleToPanel(oDOMPanel, oModul){
            // ----------------- ----------------- -----------------
            // Schnittstelle:
            // Wenn das Modul schon hinzugefügt wurde:
            // nicht erneut hinzufügen
            if($('#modul_' + oModul.id).length){
                alert("Jedes Modul kann nur einmal pro Studiengang verwendet werden.");
                return;
            }
            g_sDescription += '\\par - Füge Modul "' + oModul.name + '" hinzu\n';
            
            // Lösche ggf. das Modul aus der 
            // Auswahlliste.
            if(!g_Duplicate_Modules){
                var t = $('#tModule tbody > tr');
                for(var ii=0,len=t.length;ii<len;ii++){
                    if($(t[ii]).find('span').data('shj_item').id==oModul.id){
                        $(t[ii]).hide();
                        break;
                    }
                }
            }
            
            var sModulHtml='';
            var oModulTmp=$('#template_modul').clone();

            // Global werden die Leistungspunkte erhöht
            addLP(oModul.min_ects);
            
            // Die Leistungen des moduls werden als
            // Tabelle hinzugefügt
            for(var ii=0;ii<oModul.leistungen.length;ii++){
                sModulHtml+='<tr><td>' + oModul.leistungen[ii].leistung.name + '</td><td>' +  oModul.leistungen[ii].leistung.cp + ' LP</td></tr>';
            }
            oModulTmp.find('.table').append(sModulHtml);
            
            // Die Leistungspunkte sind eine Eigenschaft 
            // des Moduls und entsprechen nicht 
            // notwendigerweise der Summe der Leistungen, 
            oModulTmp.find('.template_modul_lp').text(' (' + oModul.min_ects + ' LP)');
            oModulTmp.find('.template_modulname')
                    .text(oModul.name)
                    .attr('href','#collapseMod' + oModul.id);
            
            // Bezug für die Logik des Ein- und Ausklappens:
            oModulTmp.find('#collapseMod').attr('id', 'collapseMod' + oModul.id);
            
            oModulTmp.attr('id', oModul.id);
            
            // Ereignisse beim Untermenü einstellen
            oModulTmp.find('.shj_LeistungenLP').on('click', function(){showEdiereModul(oModul);});
            oModulTmp.find('.shj_deleteModule').on('click', function(){
                $('#' + oModul.id).slideUp(400, function(){
                     dropModul(oModul);
                });});
            oModulTmp.find('.shj_paintRed').on('click', function(){$('#' + oModul.id).find(".shj_color").removeClass("panel-success panel-warning").addClass("panel-danger");});
            oModulTmp.find('.shj_paintGreen').on('click', function(){$('#' + oModul.id).find(".shj_color").removeClass('panel-danger panel-warning').addClass('panel-success');});
            oModulTmp.find('.shj_paintYellow').on('click', function(){$('#' + oModul.id).find(".shj_color").removeClass('panel-success panel-danger').addClass('panel-warning');});
            
            // Gebe eine eindeutige ID und hänge das 
            // Modul-Objekt an den DOM ein
            oModulTmp.find('.shj_color').attr('id', 'modul_' + oModul.id);
            oModulTmp.find('.shj_color').data('shj_item', oModul);
            oDOMPanel.append(oModulTmp);
            oModulTmp.find('.shjLeistungsContainer').droppable({
                accept: ".shj_leistung",
                hoverClass: "shj_hover",
                drop: function( event, ui ) {
                    // Funktion schreiben:
                    // addLeistungToModul($(oDOMPanel).parent('div').parent('div').data('shj_item'), oModul);
                    addLeistungToModul($(this).parent('div').parent('div').data('shj_item'), $(ui.draggable).data('shj_item'))
                }
            });
        }
        
        // Lese die LP Zahl aus 
        // "(N LP)" aus;
        // das entspricht der aktuellen LP-Zahl
        // des Moduls, wie es im Panel erscheint.
        // (Möglicherweise wurden bereits 
        // Leistungen hinzugefügt oder entfernt).
        function getCurrentLP(modul_id){
              return parseInt($('#modul_' + modul_id).find('.template_modul_lp').text().match(/\((.+?)LP\)/)[1]);
        }
        
        // Dem Modul wird (als Drag'n Drop Reaktion)
        // eine weitere Leistung hinzugefügt.
        function addLeistungToModul(oModul, oLeistung){
            // ----------------- ----------------- -----------------
            // Schnittstelle:
            // Leistung nicht doppelt
            // hinzufügen
            if($('#modul_' + oModul.id + ':contains(' + oLeistung.name + ')').length){
                alert("Jede Leistung kann nur einmal pro Modul vergeben werden.");
                return;
            }
            
            g_sDescription += '\\par - Füge Modul "' + oModul.name + '" die Leistung "' + oLeistung.name + '" hinzu.\n';
            
            // Dem Modul sowie global sind LP zu erhöhen:
            var f_lp_add=parseInt(oLeistung.cp);
            
            // Global werden die LP des Studiengangs
            // erhöht:
            addLP(f_lp_add);
            
            // Die bisherige LP Zahl des Moduls muss 
            // aus "(N LP)" ausgelesen werden (f_lp_alt):
            var f_lp_alt=getCurrentLP(oModul.id);
            
            // Die neue Summe der LP des Moduls wird
            // in die Titelzeile geschrieben
            $('#modul_' + oModul.id).find('.template_modul_lp').text(' (' + parseInt(f_lp_add + f_lp_alt) + ' LP)');
            
            // Farbe auf blau
            // als Zeichen der Änderung
            $('#modul_' + oModul.id).removeClass("panel-default panel-success panel-danger panel-warning").addClass("panel-info");
            
            // Leistung ins HTML schreiben
            $('#modul_' + oModul.id).find('.table').append('<tr><td>' + oLeistung.name + '</td><td>' +  oLeistung.cp + ' LP</td></tr>');
            
            // dem Modul wird die Leistung hinzugefügt
            oModul.isDirty = true;
            var leistung={}; leistung.leistung={}; $.extend(leistung.leistung,oLeistung);
            oModul.leistungen.push(leistung);
        }
        
        // Dem Modul wird (als Drag'n Drop Reaktion)
        // eine weitere Leistung hinzugefügt.
        function dropLeistungFromModul(oModul, oLeistung){
            //alert('Dopping Leistung ' + oLeistung.name + ' from Modul ' + oModul.name);
            
            // Dem Modul sowie global sind LP zu erhöhen:
            var f_lp_subt=parseInt(oLeistung.cp)*(-1);
            
            // Global werden die LP des Studiengangs
            // erhöht:
            addLP(f_lp_subt);
            
            g_sDescription += '\\par - Entferne aus Modul "' + oModul.name + '" die Leistung "' + oLeistung.name + '".\n';
            
            // Die bisherige LP Zahl des Moduls muss 
            // aus "(N LP)" ausgelesen werden (f_lp_alt):
            var f_lp_alt=getCurrentLP(oModul.id);
            
            // Die neue Summe der LP des Moduls wird
            // in die Titelzeile geschrieben
            $('#modul_' + oModul.id).find('.template_modul_lp').text(' (' + parseInt(f_lp_subt + f_lp_alt) + ' LP)');
            
            // Farbe auf blau
            // als Zeichen der Änderung
            $('#modul_' + oModul.id).removeClass("panel-default panel-success panel-danger panel-warning").addClass("panel-info");
            
            // Leistung aus HTML löschen
            $('#modul_' + oModul.id + ' tr:has(td:contains("' + oLeistung.name + '"))').remove();
            
            // Entferne die Leistung aus dem Modul
            oModul.isDirty = true;
            for(var ii=0;ii<oModul.leistungen.length;ii++){
                if(oModul.leistungen[ii].leistung.id===oLeistung.id){
                    oModul.leistungen.splice(ii,1);
                }
            }
        }

        function populateModule(iFachID){
            $('#tModule').empty();
            g_iFachID_Basis=iFachID;
            var template=$('<tr><td><span class="draggable shjModul"></span></td></tr>');
            for (var ii=0,len=$.signUpGlobal.info.seminar.faecher.faecher.length;ii<len;ii++){
                if($.signUpGlobal.info.seminar.faecher.faecher[ii].id==iFachID){
                    for(jj=0;jj<$.signUpGlobal.info.seminar.faecher.faecher[ii].module.length;jj++){
                        var tmp=template.clone();
                        tmp.find('.draggable').text($.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul.name).data('shj_item', $.signUpGlobal.info.seminar.faecher.faecher[ii].module[jj].modul);
                        $('#tModule').append(tmp);
                    }
                }
            }
            $( ".draggable" ).draggable({ appendTo: "body", helper: "clone", revert:'invalid' });
        }
        
        // Escape Umlaute
        function getRTFString(s_IN){
            var sReturn=s_IN;
            sReturn = sReturn.replace(/Ä/g, '\\\'c4');
            sReturn = sReturn.replace(/ä/g, '\\\'e4');
            sReturn = sReturn.replace(/Ö/g, '\\\'d6');
            sReturn = sReturn.replace(/ö/g, '\\\'f6');
            sReturn = sReturn.replace(/Ü/g, '\\\'dc');
            sReturn = sReturn.replace(/ü/g, '\\\'fc');
            return sReturn;
        }
                // Konvertiert oTermin ins iCal Format.
        function getRTF(){
            var sModularisierung='';
            var iSummeLP=parseInt(0);
            // Durchlaufe die 3 Spielplätze playground_1, 2, 3
            for(var ii=1;ii<4;ii++){
                $('#playground_' + ii + " div .shj_color").each(function(index){
                    var oMod=$(this).data('shj_item');
                    sModularisierung += '\\par \n\\b\\scaps ' + getRTFString(oMod.name) + ' (' + oMod.min_ects + ' LP)\\par \\b0\\scaps0\n';
                    iSummeLP+=parseInt(oMod.min_ects);
                    for(var ij=0;ij<oMod.leistungen.length;ij++){
                        sModularisierung += ' - ' + getRTFString(oMod.leistungen[ij].leistung.name) + ' (' + oMod.leistungen[ij].leistung.cp + ' LP)\\par\n';
                    }
                });
            }


            var sRTF = "{\\rtf1\\ansi\n"
                    + "\\deff0\\uc1\n"
                    + "{\\fonttbl\n"
                    + "{\\f0\\fswiss\\fcharset0Helvetica;}}\n"
                    + "{\\*\\generator Ted 2.23, Feb 4, 2013 (http://www.nllgg.nl/Ted);}\n"
                    + "{\\info\n"
                    + "{\\creatim\\yr2014\\mo5\\dy30\\hr18\\min55\\sec59}\n"
                    + "{\\revtim\\yr2014\\mo5\\dy30\\hr18\\min57\\sec23}\n"
                    + "}\n"
                    + "\\paperw11906\\paperh16838\n"
                    + "\\fet0\\aftntj\\aftnnrlc\n"
                    + "\\sectd\n"
                    + "\\saftnnar\\f0\\fs32\\b Aktuelles Design (" + iSummeLP + " LP)\\par \n"
                    + "\\fs24\\b0\\par\n"
                    + sModularisierung
                    + '\\par\n'
                    + g_sDescription 
                    + "}";

                    // data:text/rtf fkt nicht mit LibreOffice5
                    
            // window.open( "data:text/rtf;charset=utf8," + escape(sRTF));
            var sUmlaute = escape(sRTF);
            var blob = new Blob([sRTF], {type: "text/rtf;charset=iso-8859-15"});
            saveAs(blob,"DESGIN.rtf");
        }
        

  return my;
}($, window.$.signUpGlobal));

