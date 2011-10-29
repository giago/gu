<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<jsp:include page="/fragments/da.css"/>
		<title>Gu android application</title>
		<meta name="description" content="Gu android application">
		<meta name="google-site-verification" content="_2xl145Mddf4kPSbu7T3WlLme2wN1QPZusLJGgMefZ8" >
		<jsp:include page="/fragments/facebook-meta.jsp"/>
		<jsp:include page="/fragments/style.css"/>
	</head>
	<body>
		<div id="header">
			<jsp:include page="/fragments/header-info.jsp"/>
			<div id="menu">
				<div id="menu-btn">
					<a href="/more.jsp">More</a>
				</div>
			</div>
		</div>
		<div id="header-background"></div>
		<center>
			<div id="content">
				<div class="title">What is this about?</div>
				<div class="sentence">
					Gu is a small app that is aming to make the usability of searches and the navigation of the results
					more close to what android users are used to with native applications.
				</div>
				<div class="sentence">
					To install the app you can search "Google Usable search" or go to the android market :
				</div>
				<div class="sentence">
					<button type="button" class="btn"
						onclick="window.location.href='https://market.android.com/details?id=org.chickymate.gu';">
						<span><span>Android Market</span></span>
					</button>
					<img src="http://chart.apis.google.com/chart?chs=250x250&amp;cht=qr&amp;chl=https://market.android.com/details?id=org.chickymate.gu" title="File download URL">
				</div>
				<div class="title">What you get with the application?</h2></div>
				<div class="sentence">
					<ul>
						<li>Normal google search within a native application not the browsers</li>
						<li>Nice navigation between search results</li>
						<li>Management of search keywords history</li>
						<li>Caching of search results</li>
						<li>Bookmarking of results</li>
					</ul>
				</div>
				<div class="title">A lot more to come...</div>
				<div class="sentence">
					<ul>
						<li>Twitter search</li>
						<li>Nice Bookmarking history and integration with chrome on your computer</li>
					</ul>
				</div>
			</div>
		</center>
		<jsp:include page="/fragments/ads.jsp"/>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/analytics.jsp"/>
	</body>
</html>
