<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Gu android application</title>
		<meta name="description" content="Gu android application">
		<meta name="google-site-verification" content="_2xl145Mddf4kPSbu7T3WlLme2wN1QPZusLJGgMefZ8" >
		<jsp:include page="/fragments/facebook-meta.jsp"/>
		<jsp:include page="/fragments/style.css"/>
		<script type="text/javascript">
  (function() {
    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
    po.src = 'https://apis.google.com/js/plusone.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  })();
</script>
	</head>
	<body>
		<div id="header">
			<jsp:include page="/fragments/header-info.jsp"/>
			<div id="menu">
				<div id="menu-btn">
					<a href="http://twitter.com/share" class="twitter-share-button" data-url="http://android-gu.appspot.com/" data-text="Gu for android" data-count="horizontal" data-via="Giago">Tweet</a>  
				</div>
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
		<div style="position: absolute; top: 150; right: 150; border: 0;">
			<g:plusone size="medium"></g:plusone>
		</div>
                <a id="github" href="https://github.com/giago/gu"><img style="position: absolute; top: 0; right: 0; border: 0;" src="images/ribbon.png" alt="Fork me on GitHub"></a>
	</body>
</html>
