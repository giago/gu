<%if (request.getServerName().contains(".com") && !request.getServerName().contains(".latest")) {%>
	<div class="da-ads">
		<script type="text/javascript"><!--
		google_ad_client = "ca-pub-2374318088795044";
		/* 468x60, created 5/10/10 */
		google_ad_slot = "8619137694";
		google_ad_width = 468;
		google_ad_height = 60;
		//-->
		</script>
		<script type="text/javascript"
		src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
		</script>
	</div>
<%} else {%>
	<div style="width: 468px; height: 60px; border: 1px green solid;">space for google ads</div>	
<% } %>