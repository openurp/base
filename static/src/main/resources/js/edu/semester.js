
jQuery.fn.extend({semester_picker:function(options,initCallback){
    var bar = jQuery(this).next();
    options.id=this[0].id
    var yearInputId="#"+options.id+"_year";
    var termInputId="#"+options.id+"_term";
    var targetInputId="#"+options.id+"_target";
    var yearTableId="#"+options.id+"_yearTb"
    var termTableId="#"+options.id+"_termTb"
    
    var yearTb = bar.find("table").first();
    var termTb = yearTb.nextUntil("table").next();
    jQuery(this).next().find(targetInputId)[0].onchange = function(){
        if(!jQuery(this).data("changing")){
            jQuery(this).data("changing",true);
            if(jQuery.type(options.onChange)=="string" && jQuery.trim(options.onChange)!==""){
                eval(options.onChange);
            }else if(jQuery.type(options.onChange)=="function"){
                options.onChange.call();
            }
            jQuery(this).data("changing",false);
        }
    };

    function getSemesters(e,options,initCallback){
        if(!options.semesters){
          if(options.url){
              jQuery.ajax({
                  url: options.url,
                  success: function (result) {
                    options.semesters=eval(result);
                  },
                  async: false
              });
          }else{
            return;
          }
        }
        var result = options.semesters;
        var nonEmpty=(options.empty=="false" || options.empty==false  || options.empty==0);
        var semesters = {};
        var schoolYearList=[];
        var tdidx=0;
        var defaultValue=jQuery(targetInputId).val();
        var defaultTerm=null;
        var yearIndex=0;
        if(!nonEmpty){
          semesters.y0=[];
          tdidx+=1;
        }
        for(keyStr in result){
          var key=parseInt(keyStr);
          var s= result[key];
          if(defaultValue == s.id){
            yearIndex=tdidx;
            defaultTerm=s;
          }
          var terms=null;
          var yearKey='y'+tdidx;
          if(typeof semesters[yearKey] == 'undefined'){
            schoolYearList.push(s.schoolYear);
            terms=[]
            semesters[yearKey]=terms;
          }else{
            terms=semesters[yearKey];
          }
          terms.push(s);
          if( typeof result[key+1] != 'undefined' && result[key+1].schoolYear != s.schoolYear){
            tdidx+=1;
          }
        }
        if(nonEmpty && null == defaultTerm){
          defaultTerm = result[0];
          yearIndex=0;
        }
        var year = jQuery(e).next().find(yearInputId);
        var term = jQuery(e).next().find(termInputId);
       
        if(defaultTerm){
          var terms=semesters['y'+yearIndex];
          var termDom="<tbody>"
          for(var i=0;i<terms.length;i++){
            var s= terms[i];
            if(s.id==defaultTerm.id){
              term.attr("index",i);
            }
            termDom+="<tr><td index='"+i+"' val='"+s.id+"' class='"+(s.id==defaultTerm.id?"ui-state-active":"semester-picker-td-blankBorder")+"'><span>"+s.name+"</span>学期</td></tr>";
          }
          year.attr("index",yearIndex);
          year.val(defaultTerm.schoolYear);
          term.val(defaultTerm.name);
          e.val(defaultTerm.schoolYear+"学年"+defaultTerm.name+"学期");
          e.next().find(targetInputId).val(defaultTerm.id);
          var terms = jQuery(e).next().find(termTableId);
          terms.empty();
          terms.append(termDom+"</tbody>");
          
        }else{
          year.attr("index","-1");
          term.attr("index","-1");
          e.val("全部学期");
          e.next().find(targetInputId).val("");
        }
        var years = jQuery(e).next().find(yearTableId+" tbody");
        years.empty();
        var yearDomStr="<tr>";
        tdidx=0;
        if(!nonEmpty){
          yearDomStr+="<td class='semester-picker-td-blankBorder' id='"+options.id+"_all'>全部学期</td>";
          tdidx+=1;
        }
        for(var i=0;i< schoolYearList.length;i++,tdidx++){
          if(tdidx%3==0){
            yearDomStr+="<tr>";
          }
          yearDomStr +="<td class='semester-picker-td-blankBorder' index='"+i+"'>"+schoolYearList[i]+"</td>";
          if(tdidx%3==2){
            yearDomStr+="</tr>";
          }
        }
        jQuery(e).data("semesters",semesters);
        years.append(yearDomStr);
    }
    
    getSemesters(this,options,initCallback);
    
    jQuery(this).focus(function(){
        var bar = jQuery(this).next();
        if(!bar.data("barBlur")){
            jQuery(this).trigger("showBar");
        }else{
            jQuery(this).blur();
        }
        bar.data("barBlur",false)
    }).hover(function(){
        var bar = jQuery(this).next();
        if(bar.is(":hidden")){
            jQuery(this).data("mouseOverEvent",true).addClass("sp-text-state-highlight").removeClass("sp-text-state-default");
        }
        setTimeout("jQuery('#"+this.id+"').trigger('handlerMouseOverEvent')",300);
    },function(){
        jQuery(this).removeData("mouseOverEvent");
    }).bind("handlerMouseOverEvent",function(){
        if(jQuery(this).data("mouseOverEvent")){
            var bar = jQuery(this).next();
            if(bar.is(":hidden")){
                jQuery(this).trigger("showBar");
            }
        }
        jQuery(this).removeClass("sp-text-state-highlight").addClass("sp-text-state-default");
    }).bind("showBar",function(event){
        var bar = jQuery(this).next();
        var year = bar.find("table:first");
        var term = year.nextUntil("table").next();
        bar.scrollTop(bar.prev().scrollTop()+bar.prev().outerHeight()+3);
        bar.scrollLeft(bar.prev().scrollLeft());
        year.show();
        var semesterYear = bar.parent().find(yearInputId);
        var semesterTerm = bar.parent().find(termInputId);
        var oldVal = bar.children(targetInputId).val();
        bar.data("oldVal",oldVal);
        if(oldVal==""){
            var td = year.find("td:first");
            semesterYear.val(td.html());
            semesterTerm.children(termInputId).attr("index","0");
            td.click();
            jQuery(this).val("全部学期");
            semesterTerm.removeClass("sp-input-hover");
            bar.children(targetInputId).val("");
        }
        var yearIndex = parseInt(semesterYear.attr("index"));
        var termIndex = parseInt(semesterTerm.attr("index"));
        year.find(".ui-state-active").each(function(){
            jQuery(this).removeClass("ui-state-active");
        });
        term.find(".ui-state-active").each(function(){
            jQuery(this).removeClass("ui-state-active");
        });
        var semesterId = bar.find(targetInputId).val();
        if(semesterId==""){
            year.find("td").each(function(index,e){
                if(!jQuery(this).attr("index") && index==0){
                    jQuery(this).addClass("ui-state-active");
                }else{
                    jQuery(this).addClass("semester-picker-td-blankBorder");
                }
            });
        }else{
            year.find("td:eq("+yearIndex+")").addClass("ui-state-active");
            term.find("td[val='"+semesterId+"']").addClass("ui-state-active");
        }
        bar.show();
        bar.focus();
    })
    
    if(yearTb.find("tr").length>=termTb.find("tr").length){
        yearTb.css("border-right","1px solid #DDD");
        termTb.css("border-left","0 none");
    }else{
        termTb.css("border-left","1px solid #DDD");
        yearTb.css("border-right","0 none");
    }
    
    bar.find("#"+options.id+"_all").click(function(){
        var bar = jQuery(this).parents("div").first();
        bar.prev().val("全部学期");
        bar.find(targetInputId).val("");
        var target = bar.children(targetInputId);
        bar.hide();
        if(bar.data("oldVal")!=""){
            bar.data("oldVal","");
            if(!jQuery(this).data("changing")){
                target[0].onchange();
            }
        }
    });

    yearTb.find("td").click(function(){
        if(!jQuery(this).is(":empty")){
            //TODO 多选
            yearTb.find("td").each(function(){
                jQuery(this).removeClass("ui-state-active").addClass("semester-picker-td-blankBorder");
            });
            jQuery(this).addClass("ui-state-active").removeClass("semester-picker-td-blankBorder");
            var tb = yearTb;
            var bar = tb.parent();
            var input = bar.find(yearInputId);
            input.val(jQuery(this).html());
            var tds = tb.find("td:not(:empty)");
            var sameYear = input.attr("index")==(tds.index(jQuery(this))+"");
            if(sameYear && jQuery(targetInputId).val()!=""){
                return;
            }
            input.attr("index",tds.index(jQuery(this)));
            jQuery(this).removeClass("sp-td-hover");
            
            tb.nextUntil("table").next().empty();
            if(jQuery(this).attr("index")){                
                var terms = bar.prev().data("semesters")["y"+input.attr("index")];
                var html = "<tbody>";
                
                for(var i=0;i<terms.length;i++){
                    html+="<tr><td index='"+i+"' val='"+terms[i].id+"' class='"+(i==0?"ui-state-active":"semester-picker-td-blankBorder")+"'><span>"+terms[i].name+"</span>学期</td></tr>";
                }
                if(tb.find("tr").length>=((options.empty?1:0)+terms.length)){
                    tb.css("border-right","1px solid #DDD");
                    tb.nextUntil("table").next().css("border-left","0 none");
                }else{
                    tb.nextUntil("table").next().css("border-left","1px solid #DDD");
                    tb.css("border-right","0 none");
                }
                tb.nextUntil("table").next().append(html+"</tbody>");
                tb.nextUntil("table").next().find("td").unbind("click").click(function(){
                    if(!jQuery(this).is(":empty")){
                        termTb.find("td").each(function(){
                            jQuery(this).removeClass("ui-state-active").addClass("semester-picker-td-blankBorder");
                        });
                        jQuery(this).addClass("ui-state-active").removeClass("semester-picker-td-blankBorder");
                        if(!jQuery(this).hasClass("allSemester")){
                            var parents = jQuery(this).parentsUntil("table");
                            var tb = jQuery(parents[parents.length-1]).parent();
                            var input = tb.parent().find("#"+tb.prop("id").replace("Tb",""));
                            input.val(jQuery(this).children("span").html());
                            input.attr("index",jQuery(this).index());
                            jQuery(this).removeClass("sp-td-hover");
                            var bar = tb.parent();
                            var term = bar.find(termInputId);
                            bar.prev().val(bar.find(yearInputId).val()+"学年"+term.val()+"学期");
                            bar.children(targetInputId).val(jQuery(this).attr("val"));
                            var target = bar.children(targetInputId);
                            var newVal = target.val();
                            bar.hide();
                            if(bar.data("oldVal")!=newVal){
                                bar.data("oldVal",newVal);
                                if(!jQuery(this).data("changing")){
                                    target[0].onchange();
                                }
                            }
                        }
                    }
                }).hover(function(){
                    if(!jQuery(this).is(":empty") && !jQuery(this).hasClass("ui-state-active")){
                        jQuery(this).addClass("sp-td-hover").removeClass("semester-picker-td-blankBorder");
                        jQuery(this).addClass("sp-backgroundDDD");
                    }
                },function(){
                    jQuery(this).removeClass("sp-td-hover");
                    if(!jQuery(this).is(":empty")){
                        jQuery(this).removeClass("sp-backgroundDDD");
                        if(!jQuery(this).hasClass("ui-state-active")){
                            jQuery(this).addClass("semester-picker-td-blankBorder");
                        }
                    }
                });
                var termInput = bar.find(termInputId);
                termInput.val(terms[0].name);
                bar.children(targetInputId).val(terms[0].id);
                bar.prev().val(input.val()+"学年"+terms[0].name+"学期");
                termInput.focus();
            }
        }
        yearTb.parent().focus();
    }).hover(function(){
        if(!jQuery(this).is(":empty") && !jQuery(this).hasClass("ui-state-active")){
            jQuery(this).addClass("sp-td-hover").removeClass("semester-picker-td-blankBorder");
            jQuery(this).addClass("sp-backgroundDDD");
        }
    },function(){
        jQuery(this).removeClass("sp-td-hover");
        if(!jQuery(this).is(":empty")){
            jQuery(this).removeClass("sp-backgroundDDD");
            if(!jQuery(this).hasClass("ui-state-active")){
                jQuery(this).addClass("semester-picker-td-blankBorder");
            }
        }
    });
    
    termTb.find("td").click(function(){
        if(!jQuery(this).is(":empty")){
            //TODO 多选
            termTb.find("td").each(function(){
                jQuery(this).removeClass("ui-state-active").addClass("semester-picker-td-blankBorder");
            });
            jQuery(this).addClass("ui-state-active").removeClass("semester-picker-td-blankBorder");
            if(!jQuery(this).hasClass("allSemester")){
                var parents = jQuery(this).parentsUntil("table");
                var tb = jQuery(parents[parents.length-1]).parent();
                var input = tb.parent().find("#"+tb.prop("id").replace("Tb",""));
                input.val(jQuery(this).children("span").html());
                input.attr("index",jQuery(this).index());
                jQuery(this).removeClass("sp-td-hover");
                var bar = tb.parent();
                var term = bar.find(termInputId);
                bar.prev().val(bar.find(yearInputId).val()+"学年"+term.val()+"学期");
                bar.children(targetInputId).val(jQuery(this).attr("val"));
                var target = bar.children(targetInputId);
                var newVal = target.val();
                bar.hide();
                if(bar.data("oldVal")!=newVal){
                    bar.data("oldVal",newVal);
                    if(!jQuery(this).data("changing")){
                        target[0].onchange();
                    }
                }
            }
        }
    }).hover(function(){
        if(!jQuery(this).is(":empty") && !jQuery(this).hasClass("ui-state-active")){
            jQuery(this).addClass("sp-td-hover").removeClass("semester-picker-td-blankBorder");
            jQuery(this).addClass("sp-backgroundDDD");
        }
    },function(){
        jQuery(this).removeClass("sp-td-hover");
        if(!jQuery(this).is(":empty")){
            jQuery(this).removeClass("sp-backgroundDDD");
            if(!jQuery(this).hasClass("ui-state-active")){
                jQuery(this).addClass("semester-picker-td-blankBorder");
            }
        }
    });
    
    bar.blur(function(){
        var $this = jQuery(this);
        if($this.find("td.sp-td-hover").length==0){
            var target =  $this.children(targetInputId);
            var newVal = target.val();
            $this.hide();
            $this.data("barBlur",true);
            if($this.data("oldVal")!=newVal){
                $this.data("oldVal",newVal);
                if(!jQuery(this).data("changing")){
                    target[0].onchange();
                }
            }
        }
    });
    
    if(jQuery.type(initCallback)=="string"){
        jQuery(this).next().find(targetInputId).one("initCallback",function(){
            eval(initCallback);
        });
    }else if(jQuery.type(initCallback)!="undefined"){
        jQuery(this).next().find(targetInputId).one("initCallback",initCallback);
    }else{
        jQuery(this).next().find(targetInputId).one("initCallback",jQuery.noop);
    }
    jQuery(this).next().find(targetInputId).trigger("initCallback");
}});