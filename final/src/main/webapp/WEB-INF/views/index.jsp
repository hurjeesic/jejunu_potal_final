<%--
  Created by IntelliJ IDEA.
  User: jimen
  Date: 2020-06-10
  Time: 오후 12:57
  reference: https://www.jqueryscript.net/time-clock/dynamic-event-calendar-bootstrap.html
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dynamic Event Calendar For Bootstrap 4 Example</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/calendar/calendar.css">
    <link rel="stylesheet" href="css/calendar/theme.css">
    <link rel="stylesheet" href="css/calendar/spinner.css">
    <link rel="stylesheet" href="css/calendar/style.css">
</head>

<body>
<div id="jquery-script-menu">
    <div class="jquery-script-center">
        <ul>
            <li><a href="https://www.jqueryscript.net/time-clock/dynamic-event-calendar-bootstrap.html">Download This
                Plugin</a></li>
            <li><a href="https://www.jqueryscript.net/">Back To jQueryScript.Net</a></li>
        </ul>
        <div id="carbon-block"></div>
        <div class="jquery-script-ads">
            <script type="text/javascript"><!--
			google_ad_client = "ca-pub-2783044520727903";
            /* jQuery_demo */
			google_ad_slot = "2780937993";
			google_ad_width = 728;
			google_ad_height = 90;
			//-->
            </script>
            <script type="text/javascript"
                    src="https://pagead2.googlesyndication.com/pagead/show_ads.js">
            </script>
        </div>
        <div class="jquery-script-clear"></div>
    </div>
</div>
<div class="container" style="margin:150px auto">

    <!-- For Demo Purpose -->
    <header class="text-center text-white mb-2">
        <h1 class="display-4">Dynamic Event Calendar For Bootstrap 4 Example</h1>
        <p class="lead">A minimal clean calendar component for Bootstrap 4 that dynamically displays events on a month
            view.</p>
    </header>

    <!-- Calendar -->
    <div id="calendar" style="background-color:#fafafa"></div>


</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-2/js/all.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
<script type="module" src="js/calendar/main.js"></script>
<script type="text/javascript">

	var _gaq = _gaq || [];
	_gaq.push(['_setAccount', 'UA-36251023-1']);
	_gaq.push(['_setDomainName', 'jqueryscript.net']);
	_gaq.push(['_trackPageview']);

	(function () {
		var ga = document.createElement('script');
		ga.type = 'text/javascript';
		ga.async = true;
		ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(ga, s);
	})();

</script>
<script>
	try {
		fetch(new Request("https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js", {
			method: 'HEAD',
			mode: 'no-cors'
		})).then(function (response) {
			return true;
		}).catch(function (e) {
			var carbonScript = document.createElement("script");
			carbonScript.src = "//cdn.carbonads.com/carbon.js?serve=CK7DKKQU&placement=wwwjqueryscriptnet";
			carbonScript.id = "_carbonads_js";
			document.getElementById("carbon-block").appendChild(carbonScript);
		});
	}
	catch (error) {
		console.log(error);
	}
</script>
</body>
</html>