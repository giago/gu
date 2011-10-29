<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Android gu Help</title>
		<jsp:include page="/fragments/da.css"/>
	</head>
	<body>
		<center>
			<jsp:include page="/fragments/header.jsp"/>
			<div class="da-body">
				<jsp:include page="/fragments/left.jsp"/>
				<div class="da-article">
					<div class="da-articleTitle"><h1>Android GU Help</h1></div>
					<jsp:include page="/fragments/ads.jsp"/>
					<div class="da-articleTitle"><h2>Search</h2></div>
					<div class="da-htmlContent">
						This android app is small and easy to use. You can play with your previous keyworks, edit them and load cached results.
					</div>
					<div class="da-htmlContent">
						To <b>edit</b> a search keyword select one of the elments.
					</div>
					<div class="da-htmlContent">
						To <b>reuse</b> double touch one of the item in the list, Gu will load the results in the cache.
					</div>	
					<jsp:include page="/fragments/ads.jsp"/>
					<div class="da-articleTitle"><h2>Navigate results</h2></div>
					<div class="da-htmlContent">
						Results are coming from google open api. The api is limited. In some particular case this limitation can be problematic for some 
						search. To overcome that we have a small button on top of the list "try with google" that will open the browser and load google search engine with the query 
					</div>
				</div>
				<jsp:include page="/fragments/right.jsp"/>
			</div>
		</center>
		<jsp:include page="/fragments/analytics.jsp"/>
	</body>
</html>