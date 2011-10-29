<html>
	<head>
		<title>About Gu for Android</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<jsp:include page="/fragments/style.css"/>
	</head>
	<body>
		<div id="header-background-small">
			<div id="header-small">
				<jsp:include page="/fragments/header-info.jsp"/>
			</div>
			<div id="menu">
				<div id="menu-btn">
			    	<a href="index.jsp">Home</a>
				</div>
			</div>
		</div>
		<center>
			<div id="content">
				<div class="title">About Gu for Android</div>
				<div class="sentence">Table of contents
					<ul>
						<li>Search</li>
						<li>Navigate results</li>
						<li>Bookmarks</li>
					</ul>
				</div>
				<div class="title">Search</div>
				<div class="sentence">
					<center>
						<img src="/images/device.png" alt="screenshot of the home activity" style="border:1px solid gray;"/>
					</center>
				</div>	
				<div class="sentence">
					The application is small and easy to use. You can play with your previous keyworks, edit them and load cached results.
				</div>
				<div class="sentence">
					You can normaly search by typing the keywords or you can select one in the list.
				</div>
				<div class="sentence">
					To <b>edit</b> a search keyword select one of the elments.
				</div>
				<div class="sentence">
					To <b>reuse</b> double touch one of the item in the list, Gu will load the results in the cache.
				</div>
				<div class="title">Results navigation</div>
				<div class="sentence">
					<center>
						<img src="/images/device2.png" alt="screenshot of the search result" style="border:1px solid gray;"/>
					</center>
				</div>	
				<div class="sentence">
					The results are diplayed in a list view that is quite easy to use. As you go through it the application is loading more results. 
				</div>
				<div class="sentence">
					When you find what you are searching for just click on one of the results. The application will send you to the browser and load the requested page. 
				</div>
				<div class="sentence">
					If from the browser you go back to the result list you can save the result item previously selected in your bookmarks list. 
				</div>
				<div class="sentence">
					Results are coming from google open api. 
				</div>
				<div class="sentence">
					The google api is limited. In some particular case this limitation can be problematic for some search.
				</div>
				<div class="sentence"> 
					To overcome that we have a small button on top of the list "try with google" that will open the browser and load google search engine with the exact same query.
				</div>
			</div>
		</center>
		<jsp:include page="/fragments/ads.jsp"/>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/analytics.jsp"/>
	</body>
</html>