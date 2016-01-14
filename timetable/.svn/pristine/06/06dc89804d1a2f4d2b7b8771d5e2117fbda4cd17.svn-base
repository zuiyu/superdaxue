 function add(day, start, length, name, classroom, id) {
     if ($("table").find("tr").eq(start).find("td").eq(day).text() != "") {
    	 //console.log($("table").find("tr").eq(start).find("td").eq(day).attr("rowspan"));
         //alert($("table").find("tr").eq(start).find("td").eq(day).text());
    	 if($("table").find("tr").eq(start).find("td").eq(day).attr("rowspan")>=length){
    		 color = ['#13CA9A', '#BA8ADE', '#FD8C40', '#C9C9C9', '#3DB5E7', '#FFB81E',"#FF828A","#EA69A2","#FE3E65","#7E57C6","#B3D465","#F29C77"];
             var $append = $("<div></div>");
             $append.css({
                 "width": 0,
                 "height": 0,
                 "border-left": "10px solid #000",
                 "border-top": "10px solid" + color[id % 12],
                 "position": "absolute",
                 "right": "0",
                 "top": "0"
             });
             $("table").find("tr").eq(start).find("td").eq(day).prepend($append).addClass("click");
             $("table").find("tr").eq(start + 1).find("td").eq(day).css("background", color[id % 12]).text(name + classroom); 
    	 }else{
    		 color = ['#13CA9A', '#BA8ADE', '#FD8C40', '#C9C9C9', '#3DB5E7', '#FFB81E',"#FF828A","#EA69A2","##F4889F","#7E57C6","#B3D465","#F29C77"];
             var $append = $("<div></div>");
             console.log( $("table").find("tr").eq(start).find("td").eq(day).css("background-color"));
             $append.css({
                 "width": 0,
                 "height": 0,
                 "border-left": "10px solid #000",
                 "border-top": "10px solid",
                 "border-top-color":  $("table").find("tr").eq(start).find("td").eq(day).css("background-color"),
                 "position": "absolute",
                 "right": "0",
                 "top": "0"
             });
             $("table").find("tr").eq(start + 1).find("td").eq(day).css("background-color",$("table").find("tr").eq(start).find("td").eq(day).css("background-color")).text($("table").find("tr").eq(start).find("td").eq(day).text());
             $("table").find("tr").eq(start).find("td").eq(day).attr({
                 rowspan: length
             }).css("background", color[id % 12]).text(name + classroom);
    		 $("table").find("tr").eq(start).find("td").eq(day).prepend($append).addClass("click");
    		 
    		 
    	 }
    	 
     } else {
         color = ['#13CA9A', '#BA8ADE', '#FD8C40', '#C9C9C9', '#3DB5E7', '#FFB81E',"#FF828A","#EA69A2","#FE3E65","#7E57C6","#B3D465","#F29C77"];
         $("table").find("tr").eq(start).find("td").eq(day).attr({
             rowspan: length
         }).css("background", color[id % 12]).text(name + classroom);
         for (i = 1; i < length; i++) {
             $("table").find("tr").eq(start + i).find("td").eq(day).hide();
         }
     }
 }
 $(document).ready(function() {
	 $day = new Date();
 	$today = $day.getDay();
 	//alert($today);
 	if($today==0){
 		$("table th").eq(7).addClass("now");
 	}else{
 		$("table th").eq($today).addClass("now");
 	}
     if ($(window).width() > 320) {
         $("table").css({
             width: '100%'
         });


     } else {
         $("table").css({
             width: '136%'
         });
         $("table").swipe({
             swipeLeft: function(event, direction, distance, duration, fingerCount) {
                 //$(this).css("margin-left","-38%");
                 $(this).animate({
                     "margin-left": "-38%"
                 });
             },

             swipeRight: function(event, direction, distance, duration, fingerCount) {
                 //$(this).css("margin-left","0");
                 $(this).animate({
                     "margin-left": 0
                 });
             }
         });
     }
     $(".click").on("click", function() {
         $grey = $("<div></div>");
         $grey.css({
             "width": "100%",
             "height": "100%",
             "position": "fixed",
             "left": 0,
             "top": 0,
             "background": "rgba(0,0,0,0.8)"
         });
         $grey.append($(this).clone().css({
             "min-height": "8rem",
             "width": "4rem",
             "position": "absolute",
             "color": "#fff",
             "font-size": "0.8rem",
             "left": "5rem",
             "top": "5rem",
             "opacity": "1",
             "z-index": "100",
             "veritical-align": "center",
             "line-height": "1.5rem",
             "padding": "4px",
             "width": "20%"
         }).html($(this).text()));
         $find = $(this);
         $(this).closest("tr").children("td").each(function(index, el) {
             if ($find.is(el)) {
                 $grey.append($find.closest('tr').next('tr').children('td').eq(index).clone().show().css({
                     "min-height": "8rem",
                     "width": "4rem",
                     "position": "absolute",
                     "color": "#fff",
                     "font-size": "0.8rem",
                     "right": "5rem",
                     "top": "5rem",
                     "opacity": "1",
                     "z-index": "100",
                     "veritical-align": "center",
                     "line-height": "1.5rem",
                     "padding": "4px",
                     "width": "20%"
                 }));
             }
         });
         $("body").append($grey);
         $grey.on("click", function() {
             $(this).remove();
         });
     });
 });
