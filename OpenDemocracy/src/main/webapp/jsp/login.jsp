<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OpenDemocracy login</title>

    <link rel="stylesheet" type="text/css" media="screen" href="/VAADIN/openid.css" />

</head>
<body>
<div align="center">
<h2>Login</h2>
<!-- <p>
Currently openID login doesn't work if the user name that your provider gives us contains international characters.
This is because of a minor server configuration problem with this cloud service. A fix is under way.
</p> -->
<form class="openid" method="post" action="/j_spring_openid_security_check" target="_top"> 
  <fieldset> 
  <label for="openid_username">Enter your <span>Provider user name</span> or choose your provider</label> 
  <div><span></span><input type="text" name="openid_username" /><span></span> 
  <input type="submit" value="Login" /></div> 
  </fieldset> 
  <fieldset> 
  <label for="openid_identifier">Enter your <a class="openid_logo" href="http://openid.net">OpenID</a> or choose your provider</label> 
  <div><input type="text" name="openid_identifier" /> 
  <input type="submit" value="Login" /></div> 
  </fieldset> 
  <div align="center"><ul class="providers"> 
  <li class="openid" title="OpenID"><img src="/VAADIN/images/openidW.png" alt="icon" /> 
  <span><strong>http://{your-openid-url}</strong></span></li> 
  <li class="direct" title="Google"> 
		<img src="/VAADIN/images/googleW.png" alt="icon" /><span>https://www.google.com/accounts/o8/id</span></li> 
  <li class="direct" title="Yahoo"> 
		<img src="/VAADIN/images/yahooW.png" alt="icon" /><span>http://yahoo.com/</span></li> 
  <li class="username" title="MyOpenID user name"> 
		<img src="/VAADIN/images/myopenid.png" alt="icon" /><span>http://<strong>username</strong>.myopenid.com/</span></li> 
  <li class="username" title="Flickr user name"> 
		<img src="/VAADIN/images/flickr.png" alt="icon" /><span>http://flickr.com/<strong>username</strong>/</span></li> 
  <li class="username" title="Technorati user name"> 
		<img src="/VAADIN/images/technorati.png" alt="icon" /><span>http://technorati.com/people/technorati/<strong>username</strong>/</span></li> 
  <li class="username" title="Wordpress blog name"> 
		<img src="/VAADIN/images/wordpress.png" alt="icon" /><span>http://<strong>username</strong>.wordpress.com</span></li> 
  <li class="username" title="Blogger blog name"> 
		<img src="/VAADIN/images/blogger.png" alt="icon" /><span>http://<strong>username</strong>.blogspot.com/</span></li> 
  <li class="username" title="LiveJournal blog name"> 
		<img src="/VAADIN/images/livejournal.png" alt="icon" /><span>http://<strong>username</strong>.livejournal.com</span></li> 
  <li class="username" title="ClaimID user name"> 
		<img src="/VAADIN/images/claimid.png" alt="icon" /><span>http://claimid.com/<strong>username</strong></span></li> 
  <li class="username" title="Vidoop user name"> 
		<img src="/VAADIN/images/vidoop.png" alt="icon" /><span>http://<strong>username</strong>.myvidoop.com/</span></li> 
  <li class="username" title="Verisign user name"> 
		<img src="/VAADIN/images/verisign.png" alt="icon" /><span>http://<strong>username</strong>.pip.verisignlabs.com/</span></li> 
  </ul></div>
</form>
<noscript>
<h2>Login (without javascript)</h2>
<form class="openid" method="post" action="/j_spring_openid_security_check"> 
  <div><ul class="providers"> 
  <li class="openid" title="OpenID"><img src="/VAADIN/images/openidW.png" alt="icon" /> 
  <span><strong>http://{your-openid-url}</strong></span></li> 
  <li class="direct" title="Google"> 
		<img src="/VAADIN/images/googleW.png" alt="icon" /><span>https://www.google.com/accounts/o8/id</span></li> 
  <li class="direct" title="Yahoo"> 
		<img src="/VAADIN/images/yahooW.png" alt="icon" /><span>http://yahoo.com/</span></li> 
  <li class="username" title="AOL screen name"> 
		<img src="/VAADIN/images/aolW.png" alt="icon" /><span>http://openid.aol.com/<strong>username</strong></span></li> 
  <li class="username" title="MyOpenID user name"> 
		<img src="/VAADIN/images/myopenid.png" alt="icon" /><span>http://<strong>username</strong>.myopenid.com/</span></li> 
  <li class="username" title="Flickr user name"> 
		<img src="/VAADIN/images/flickr.png" alt="icon" /><span>http://flickr.com/<strong>username</strong>/</span></li> 
  <li class="username" title="Technorati user name"> 
		<img src="/VAADIN/images/technorati.png" alt="icon" /><span>http://technorati.com/people/technorati/<strong>username</strong>/</span></li> 
  <li class="username" title="Wordpress blog name"> 
		<img src="/VAADIN/images/wordpress.png" alt="icon" /><span>http://<strong>username</strong>.wordpress.com</span></li> 
  <li class="username" title="Blogger blog name"> 
		<img src="/VAADIN/images/blogger.png" alt="icon" /><span>http://<strong>username</strong>.blogspot.com/</span></li> 
  <li class="username" title="LiveJournal blog name"> 
		<img src="/VAADIN/images/livejournal.png" alt="icon" /><span>http://<strong>username</strong>.livejournal.com</span></li> 
  <li class="username" title="ClaimID user name"> 
		<img src="/VAADIN/images/claimid.png" alt="icon" /><span>http://claimid.com/<strong>username</strong></span></li> 
  <li class="username" title="Vidoop user name"> 
		<img src="/VAADIN/images/vidoop.png" alt="icon" /><span>http://<strong>username</strong>.myvidoop.com/</span></li> 
  <li class="username" title="Verisign user name"> 
		<img src="/VAADIN/images/verisign.png" alt="icon" /><span>http://<strong>username</strong>.pip.verisignlabs.com/</span></li> 
  </ul></div> 
  <fieldset> 
  <label for="openid_username">Enter your <span>Provider user name</span></label> 
  <div><span></span><input type="text" name="openid_username" /><span></span> 
  <input type="submit" value="Login" /></div> 
  </fieldset> 
  <fieldset> 
  <label for="openid_identifier">Enter your <a class="openid_logo" href="http://openid.net">OpenID</a></label> 
  <div><input type="text" name="openid_identifier" /> 
  <input type="submit" value="Login" /></div> 
  </fieldset> 
</form></noscript>
<br /><br /><br />
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<!-- 
<h2>Additional Images</h2>
<img src="/VAADIN/images/flickrW.png" alt="icon" />
<img src="/VAADIN/images/myspaceW.png" alt="icon" />
<img src="/VAADIN/images/myopenidW.png" alt="icon" />
<img src="/VAADIN/images/facebookW.png" alt="icon" /><br />
 -->
<p><a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/"><img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/80x15.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.</p>

<br/>
<br/>
<!-- 
		<img src="/VAADIN/themes/OpenDemocracyTheme/img/google-account-logo.png" />
		<p style="height: 0.25em" />
		<form action="OpenDemocracy/j_spring_openid_security_check" method="post">
			<input name="openid_identifier" size="50" maxlength="100" type="hidden" value="http://www.google.com/accounts/o8/id" />
			<div class="submit">
				<input id="proceed_google" type="submit" value="Sign in with Google" />
			</div>
		</form>
		<br />
		<p />

		<a href="http://openid.net/get-an-openid/" target="_blank" title="Where do I get one?">
			<img src="/VAADIN/themes/OpenDemocracyTheme/img/openid-logo.png" />
		</a>
		<p />
		<form action="OpenDemocracy/j_spring_openid_security_check" method="post">
			<div>
				<label for="openid_identifier"> OpenID </label>
				<input id="openid_identifier" name="openid_identifier" type="text" style="width: 150px" />
			</div>
			<br />
			<div class="submit">
				<input id="proceed_openid" type="submit" value="Submit" />
			</div>
		</form> -->
	</div>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/VAADIN/jquery.openid.js"></script>
<script type="text/javascript">  $(function() { $("form.openid:eq(0)").openid();$("form.openid:eq(0)").setAttribute("target", "_top"); });</script>
	
</body>
</html>