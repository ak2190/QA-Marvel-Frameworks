package qamarvel.framework.auth.apply;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import qamarvel.framework.auth.AuthState;

public class HeaderAuthApplier implements AuthApplier {

	@Override
	public void apply(WebDriver driver, AuthState authState) {

	    String token = authState.getToken();
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    js.executeScript(
	        "(function() {" +

	        // ---- FETCH OVERRIDE ----
	        " const originalFetch = window.fetch;" +
	        " if (originalFetch) {" +
	        "   window.fetch = function() {" +
	        "     arguments[1] = arguments[1] || {};" +
	        "     arguments[1].headers = arguments[1].headers || {};" +
	        "     arguments[1].headers['Authorization'] = '" + token + "';" +
	        "     return originalFetch.apply(this, arguments);" +
	        "   };" +
	        " }" +

	        // ---- XHR OVERRIDE ----
	        " const OriginalXHR = window.XMLHttpRequest;" +
	        " function NewXHR() {" +
	        "   const xhr = new OriginalXHR();" +
	        "   const originalOpen = xhr.open;" +
	        "   xhr.open = function() {" +
	        "     this.addEventListener('readystatechange', function() {" +
	        "       try { xhr.setRequestHeader('Authorization', '" + token + "'); } catch(e) {}" +
	        "     });" +
	        "     return originalOpen.apply(this, arguments);" +
	        "   };" +
	        "   return xhr;" +
	        " }" +
	        " window.XMLHttpRequest = NewXHR;" +

	        "})();"
	    );
	}
}