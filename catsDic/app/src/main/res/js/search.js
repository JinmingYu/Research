    function showAndroidToast(toast) {
        Android.showToast(toast);
    }
    // get video display order keyword, if present - pattern is {#}
function getVideoOrder(vid) {
    var keywords = vid.keywords,
        orderKey, order = 999;

    orderKey = keywords.filter(function(element) {
        return element.match(/^\{\d+\}$/) !== null;
    });
    if (orderKey.length > 0) {
        order = parseInt(orderKey[0].slice(1,-1), 10);
    }
    return order;
}
    function showVideos( t ) {

    console.log( "getting to here with " + t);
    $.ajax({
        method: "GET",
        url: "http://smartsign.imtc.gatech.edu/videos?keywords=" + t,
        dataType: 'jsonp',
        success: function(response) {
            console.log( response );
            console.log( response[0] );

            //var data = $.parseJSON(response.responseText);
            var data = response;

            // sort data array
            data.sort(function(a, b) {
                return getVideoOrder(a) - getVideoOrder(b);
            });

            var idArray = new Array(data.length);

            for ( var i = 0; i < data.length; i++ ) {
                idArray[i] = '<iframe width="640" height="360" align:center src="http://www.youtube.com/embed/' + data[i]['id'] + '?rel=0"> </iframe>';
                console.log( idArray[i] );
            }

            console.log( data.length);

            var htmlString = '<div style="width:100%;position=absolute;text-align:center"> <div style="position:relative;left:25%;width:640px;text-align:left;">';

            for(var k=0; k<idArray.length; k++) {
                htmlString += idArray[k];
            }

            $('#lightbox')
                .html(htmlString)
                .css({"line-height":($(window).height()*0)+"px", "overflow":"auto", "display":"block"})
                .fadeIn('fast')
                .on('click', function(){
                    $(this).fadeOut('fast');
                });
            //console.log( htmlString );
            //console.log("running fine");
            //console.log("T = " + t);



            $('#searchwindow').hide();
        }
    });

    showVideos( window.location.href. )