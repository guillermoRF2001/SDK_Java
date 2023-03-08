package com.SDK.model;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import org.json.simple.JSONValue;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_1;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;
import java.lang.Object;
import java.net.URLEncoder;
import io.vertx.core.json.*; 
/**
 * Docxpresso SERVER SDK
 *
 * @copyright  Copyright (c) 2017 No-nonsense Labs (http://www.nononsenselabs.com)
 * @license    MIT
 * @link       https://opensource.org/licenses/MIT
 * @version    5.0
 * @since      1.0
 */

 /**
 * A collection of methods that simplify the data exchange and communication
 * with the Docxpresso SERVER package
 */
public class Utils {

    public static Map<String, Object> _options;
    /**
     * Construct
     *
     * @param array $options with the following keys and values
     *      'pKey' => (string) the private key of your Docxpresso SERVER 
     *       installation 
     *      'docxpressoInstallation' => (string) the URL of your Docxpresso
     *      SERVER installation
     * 
     * @access public
     */
    public Utils(Map<String, Object> _options) {
        Utils._options = _options;
    }

    /**
     * Setter for options
     * @param array $options with the following keys and values
     *      'pKey' => (string) the private key of your Docxpresso SERVER 
     *       installation
     *      'docxpressoInstallation' => (string) the URL of your Docxpresso
     *      SERVER installation
     * 
     * @access public
     */
    
    

    

    /**
     * Checks the validity of an API key
     * 
     * @param string $key the key you wish to validate
     * @param string $data the string that was used to generate the key
     * @param string $pKey the private key used to generate the hash
     * @return boolean
     * @access public
     */
    public static Boolean apikey_control(String key, String data, String pKey)
    {   
        
        try {
            byte[] hmacSha1 = HMAC.calcHmacSha1(pKey.getBytes("UTF-8"), data.getBytes("UTF-8")); 
            if(key == String.format("Hex: %016x", new BigInteger(1, hmacSha1))){
                return true;
            } else {
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          return false;
        }
    }


    /**
     * Encodes in base64 url safe
     * 
     * @param string $str
     * @return string
     * @access public
     */
    public static String base64_encode_url_safe(String str)
    {
        String encoded = Base64.getUrlEncoder().encodeToString(str.getBytes());
        return encoded.replaceAll("[+]","-").replaceAll("[/]","_").replaceAll("[=]",",");
    }


    /**
     * Decodes base64 url safe
     * 
     * @param string str
     * @return string
     * @access public
     */
    public static String base64_decode_url_safe(String str)
    {
        return new String(Base64.getDecoder().decode(str.replaceAll("[-]","+").replaceAll("[_]","/").replaceAll("[,]","=")));
    }


    /**
     * Generates a one time link to preview a document in the associated
     * Docxpresso SERVER interface
     * 
     * NOTE: if data is loaded from various sources it will be loaded with the 
     * folllowing priority: varData, requestDataURI, token
     * 
     * @param array $data with the following keys and values
     *      'template' => (int) the id of the requested document template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'token' => (string) a unique identifier of a previous use. If given
     *       the data associated with the token will be preloaded into 
     *       the document.
     *      'identifier' => (string) optional var name that we may pass to help 
     *       identify that particular usage. Default value is an empty string 
     *      'reference' => (string) an optional string we may pass to help 
     *       identify that particular usage. Default value is an empty string
     *      'expires' => (integer) the number of seconds after which the link
     *       is no longer valid. 
     *      'custom' => (string) an optional string we may pass to add external
     *       additional info to the template
     *      'form' => (boolean) if true Docxpresso will serve a web form rather
     *       than an interactive document. Default value is false.
     *      'format' => (string) the requested document output format. The
     *       possible values include odt, pdf, doc, docx and rtf. If not given
     *       the available formats will be taken from the template settings.
     *      'enduserid' => (string) a value that will help us later to identify
     *       the user that requested the document. Default value is an empty 
     *       string.
     *      'email' => (string) the email of the user to send additional
     *       notifications. 
     *      'requestConfigURI' => (string) the URL where Docxpresso should fetch
     *       external configuration adjustments.
     *      'requestDataURI' => (string) the URL where Docxpresso should fetch
     *       external data. Default value is an empty string.
     *      'requestExternalCSS' => (string) the URL where Docxpresso should
     *       fetch for some external CSS file.
     *      'requestExternalJS' => (string) the URL where Docxpresso should
     *       fetch for some external JS file.
     *      'responseDataURI' => (string) the URL where Docxpresso should
     *       forward the user data. Default value is an empty string.
     *      'processingDataURI' => (string) the URL where Docxpresso should
     *       postprocess doc data. Default value is an empty string.
     *      'responseURL' => (string) the URL where Docxpresso should redirect
     *       the end user after saving the data. Default value is an empty 
     *       string. 
     *      'documentName' => (string) the name we want to give to the generated
     *       document. Default value is empty and in that case Docxpresso
     *       will use the default template name. 
     *       string.
     *      'domain' => (string) the URL doing the request. Default value is an 
     *       empty string.
     *      'prefix' => (string) a prefix that will limit enduser edition to
     *       only the field variables that start by that prefix. You can use
     *       a comma separated list to select more than one prefix. Default value 
     *       is an empty string.
     *      'editableVars' => (string) a comma separated list of var names
     *       to restrict the edition to that set. Default value is an empty 
     *       string.
     *      'blockVars' => (string) a comma separated list of var names
     *       which edition is expicitly blocked. Default value is an empty 
     *       string.
     *      'enforceValidation' => (boolean) if true the user will not be able
     *       to send data until all variable fields are validated. Default value
     *       is false.
     *      'language' => (string) if set will change the default interface
     *       language. Currently available values are: default, en, es
     *      'GDPR' => (boolean) if true the end user will be prompted to check
     *       the privacy policy (it only applies if it is also globally defined)
     *      'phone' => (string) with standard international format +12121112222.
     *       If given the end user will be first prompted to introduce an OTP
     *       sent to that phone.
     *      'OTPmessage' => (string) this only applies if we use a phone to
     *       request an OTP
     *      'varData' => additional JSON data we would like to preload into the 
     *       document
     *      'continueLink' => (boolean) if true the end user will be prompted
     *       to introduce an email where he can continue later the edition
     *      'continueLinkEmail' => if continueLink is set to true and this 
     *       option is given it will be shown as the default email to receive
     *       the continue link.
     *      'blockDocument' => (int) if equals "1" the end user will be asked if
     *       the document should be blocked from further edition, else if equals
     *       "2" the document wil be automatically blocked after first use and 
     *       finally if it equals "3" the document will be automatically blocked
     *       only if the end user is not a registered backoffice user.
     *      'history' => (boolean) if true and there is a token (or history data
     *       is provided from external sources) the user will be able to
     *       visualize the edition history of the document.
     *      'keepAlive' => (boolean) if true the generated link is alived and
     *       allows for multiple editions of the same document.
     *      'trackID' => (string) if given it will be used whenever the 
     *       keepAlive is set to true, otherwise it will be autogenerated.
     *      'loadContactData' => (integer) id of the contact whose data we want
     *       to load into the template.
     *      'requestVars' => (array) list of variables that should be sent in
     *       the query parameter of the responseURL
     *      'plugin' => (boolean) if true the petition may directly come from
     *       a plugin user so further security checks may be carried out to test
     *       for data coherence and permissions.
     *      'enableRejection' => (boolean) if true the user will be offered a
     *       button to directly 'reject' the document wuithout fulfilling it.
     *      'livePreview' => (boolean) this property only applies to web forms, 
     *       if true a live preview of the generated document will be offered.
     *      'client' => (integer) cliend id. Only for multi tenant instances. 
     *      'tabDisplay' => (string) if "full" the tabs will be forced to be
     *       fully displayed even if there are more than 5. default value is 
     *       "auto".
     * @return string
     * @throws UnsupportedEncodingException
     * @access public
     */
   public static String previewDocument(Map<String, Object> data) throws UnsupportedEncodingException
    {     
        String url;
        if (data.get("phone")!= null){
            url = _options.get("docxpressoInstallation") + "/tracking/OTP/request_OTP/" + data.get("template");
        } else if(data.get("form") != null && data.get("form").toString().equals("true")){
            url = _options.get("docxpressoInstallation") + "/documents/previewForm/" + data.get("template");
        } else {
            url = _options.get("docxpressoInstallation") + "/documents/preview/" + data.get("template");
        }
        Map<String, Object> options = new HashMap<>();
        if(data.get("form") != null && data.get("form").toString().equals("true")){
            options.put("display", "form");
        }else {
            options.put("display", "document");

        }
        if (data.get("phone")!= null){
            options.put("phone", data.get("phone"));
            options.put("action", "preview");

        }
        if (data.get("OTPmessage")!= null){ 
            String otpMessage = URLEncoder.encode((data.get("OTPmessage").toString()),"UTF-8");
            options.put("OTPmessage", otpMessage);
        }
        if (data.get("token") != null) {
            options.put("token",data.get("token"));
        }
        if (data.get("format") != null) {
            options.put("forceFormat",data.get("format"));
        } 
        if (data.get("enduserid")!= null) {
            options.put("enduserid",data.get("enduserid"));
        }  
        if (data.get("email")!= null) {
            options.put("email",data.get("email"));
        } 
        //notice that if continueLink is given the enduserid will be overwritten
        if (data.get("continueLink")!= null && data.get("continueLink").toString().equals("true")) {
            options.put("access","authenticated");
            options.put("enduserid",Utils._generateOTP());
        } 
        if (data.get("continueLinkEmail")!= null) {
            options.put("email",data.get("continueLinkEmail"));
        } 
        if (data.get("identifier")!= null) {
            options.put("identifier",data.get("identifier"));
        } 
        if (data.get("client")!= null) {
            options.put("client",data.get("client"));
        } 
        if (data.get("expires")!= null) {
            options.put("expires",data.get("expires"));
        }
        if (data.get("reference")!= null) {
            options.put("reference",data.get("reference"));
        }
        if (data.get("custom")!= null) {
            options.put("custom",data.get("custom"));
        }
        if (data.get("requestConfigURI")!= null) {
            Map<String, Object> dURI = new HashMap<>();
            dURI.put("URL",data.get("requestConfigURI"));
            options.put("requestConfigURI",Json.encode(dURI));
        }
        
        if (data.get("requestDataURI")!= null) {
            Map<String, Object> dURI = new HashMap<>();
            dURI.put("URL",data.get("requestDataURI"));
            dURI.put("requestData","preview");
            options.put("requestDataURI",Json.encode(dURI));
        }
        if (data.get("requestExternalJS")!= null) {
            options.put("requestExternalJS",data.get("requestExternalJS"));
        }
        if (data.get("requestExternalCSS")!= null) {
            options.put("requestExternalCSS",data.get("requestExternalCSS"));
        }
        if (data.get("responseDataURI")!= null) {
            options.put("responseDataURI",data.get("responseDataURI"));
        }
        if (data.get("processingDataURI")!= null) {
            options.put("processingDataURI",data.get("processingDataURI"));
        }
        if (data.get("responseURL")!= null) {
            options.put("responseURL",data.get("responseURL"));
        }
        if (data.get("documentName")!= null) {
            options.put("documentName",data.get("documentName"));
        }
        if (data.get("domain")!= null) {
            options.put("domain",data.get("domain"));
        } 
        if (data.get("prefix")!= null) {
            options.put("prefix",data.get("prefix"));
        }
        if (data.get("editableVars")!= null) {
            options.put("editableVars",data.get("editableVars"));
        } 
        if (data.get("blockVars")!= null) {
            options.put("blockVars",data.get("blockVars"));
        }
        if (data.get("enforceValidation")!= null) {
            options.put("enforceValidation",true);
        }
        if (data.get("GDPR")!= null) {
            options.put("GDPR",true);
        }
        if (data.get("livePreview")!= null) {
            options.put("viewDoc",true
            );
        }
        if (data.get("language")!= null) {
            options.put("locale",data.get("language"));
        }
        if (data.get("varData")!= null) {
            options.put("data",data.get("varData"));
        }
        if (data.get("history")!= null) {
            options.put("history",1);
        }
        if (data.get("blockDocument")!= null) {
            options.put("blockDocument",data.get("blockDocument"));
        }
        if (data.get("keepAlive")!= null) {
            options.put("keepAlive",1);
            //we should also generate a trackID to be able to keep track
            //of consequent editions
            if (data.get("trackID")!= null){
                options.put("trackID",data.get("trackID"));
            } else {
                int template = (int)data.get("template"); 
                long preseed = Utils.generate_uniqid() + template + Utils.generate_uniqid();
                String prehash = Utils.hmacSha1(Long.toString(preseed));
                String trackID = Utils.hmacMd5(prehash.toString());
                options.put("trackID",trackID);
            }
        }
        Object loadContactData = data.get("loadContactData");
        int loadContactDataInt;
        if (loadContactData instanceof Integer) {
            loadContactDataInt = (Integer) loadContactData;
        }else {
            loadContactDataInt = 0;
        }
        if (loadContactDataInt > 0){
            options.put("preloadDXData",1);
            Map<String, Object> DXData = new HashMap<>();
            options.put("DXData",DXData);
            DXData.put("type","contact");
            DXData.put("contact",data.get("loadContactData"));
        }
        Object requestVars = data.get("requestVars");
        String[] requestVarsStringArrays = {};
        if (requestVars instanceof String[]) {
            requestVarsStringArrays = (String[]) requestVars;
        }    
        if (requestVarsStringArrays != null && requestVarsStringArrays.length > 0) {
            StringJoiner joiner = new StringJoiner(",");
                for (String requestVarsStringArray : requestVarsStringArrays) {
                joiner.add(requestVarsStringArray);
            }
            String result = joiner.toString();
            options.put("requestVars ",result);
        }
        if (data.get("plugin") != null) {
            options.put("plugin ",1);
        }
        if (data.get("enableRejection") != null) {
            options.put("enableRejection ",1);
        }
        if (data.get("tabDisplay") != null) {
            options.put("tabDisplay ",data.get("tabDisplay"));
        }
         String opt = Utils.base64_encode_url_safe(Json.encode(options));
        
        return Utils._returnLink(url,data.get("template").toString(), opt);    
    }

    /**
     * Generates a one time link to validate a document in the associated
     * Docxpresso SERVER interface
     * 
     * @param array $data with the following keys and values
     *      'template' => (int) the id of the requested document template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'token' => (string) a unique identifier of a previous use. This 
     *       value is complusory and must correspond to a valid usage token.
     *      'requestDataURI' => (string) the URL where Docxpresso should fetch
     *       external data. Default value is an empty string.
     *      'varData' => additional JSON data we would like to preload into the 
     *       document
     *      'name' => (string) the name of the user that wll validate the 
     *       document.
     *      'email' => (string) the email of the user that wll validate the 
     *       document.
     *      'phone' => (string) with standard international format +12121112222.
     *       If given the end user will be first prompted to introduce an OTP
     *       sent to that phone. If the value is set as "request" the user will 
     *       be prompted to introduce his phone in the validation interface.
     *      'OTPmessage' => (string) this only applies if we use a phone to
     *       request an OTP
     *      'custom' => (string) an optional string we may pass to add external
     *       additional info to the validation process.
     *      'language' => (string) if set will change the default interface
     *       language. Currently available values are: default, en, es.
     *      'responseDataURI' => (string) the URL where Docxpresso should
     *       forward the user data. Default value is an empty string.
     *      'responseURL' => (string) the URL where Docxpresso should redirect
     *       the end user after validating the data. Default value is an empty 
     *       string.
     *      
     * @return string
     * @throws UnsupportedEncodingException
     * @access public
     */
    public String validateDocument( Map<String, Object> data) throws UnsupportedEncodingException
    {     
        String url = _options.get("docxpressoInstallation") + "/documents/validate/preview/";
        url += data.get("template") + "/" + data.get("token");
        
        Map<String, Object> options = new HashMap<>();
        if (data.get("name") != null) {
            options.put("name",  data.get("name")); 
        }
        if (data.get("email") != null) {
            options.put("email",  data.get("email")); 
        }
        if (data.get("phone") != null) {
            options.put("phone",  data.get("phone")); 
        }
        if (data.get("OTPmessage") != null) {
            options.put("OTPmessage",  URLEncoder.encode(data.get("OTPmessage").toString(),"UTF-8")); 
        }
        if (data.get("custom") != null) {
            options.put("custom",  data.get("custom")); 
        }
        if (data.get("responseURL") != null) {
            options.put("responseURL",  data.get("responseURL")); 
        }
        if (data.get("responseDataURI") != null) {
            options.put("responseDataURI",  data.get("responseDataURI")); 
        }
        if (data.get("requestDataURI") != null) {
            Map<String, Object> dURI = new HashMap<>();
            dURI.put("URL", data.get("requestDataURI"));
            dURI.put("requestData", "validate");
            options.put("requestDataURI", Json.encode(dURI)); 
        }
        if (data.get("varData") != null) {
            options.put("data",  data.get("varData")); 
        }
        if (data.get("language") != null) {
            options.put("locale",  data.get("language")); 
        }

        String opt = Utils.base64_encode_url_safe(Json.encode(options));
        //in this case we need to concatenate id and token so the apikey
        //can not be reused for methods that allowed to use twice the
        //apikey
        String id = data.get("template").toString() + data.get("token");
        //console.log(id);
        return Utils._returnLink(url, id, opt);    
    }

    /**
     * Generates a one time link to preview a document in the associated
     * Docxpresso SERVER interface and sends it by email to the end user
     * 
     * NOTE: if data is loaded from various sources it will be loaded with the 
     * folllowing priority: varData, requestDataURI, token
     * 
     * @param array $data with the following keys and values
     *      'template' => (int) the id of the requested document template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'token' => (string) a unique identifier of a previous use. If given
     *       the data associated with the token will be preloaded into 
     *       the document.
     *      'identifier' => (string) optional var name that we may pass to help 
     *       identify that particular usage. Default value is an empty string 
     *      'reference' => (string) an optional string we may pass to help 
     *       identify that particular usage. Default value is an empty string
     *      'expires' => (integer) the number of seconds after which the link
     *       is no longer valid. 
     *      'custom' => (string) an optional string we may pass to add external
     *       additional info to the template
     *      'form' => (boolean) if true Docxpresso will serve a web form rather
     *       than an interactive document. Default value is false.
     *      'format' => (string) the requested document output format. The
     *       possible values include odt, pdf, doc, docx and rtf. If not given
     *       the available formats will be taken from the template settings.
     *      'enduserid' => (string) a value that will help us later to identify
     *       the user that requested the document. Default value is an empty 
     *       string.
     *      'email' => (string) the email of the user to send additional
     *       notifications. 
     *      'requestConfigURI' => (string) the URL where Docxpresso should fetch
     *       external configuration adjustments.
     *      'requestDataURI' => (string) the URL where Docxpresso should fetch
     *       external data. Default value is an empty string.
     *      'requestExternalCSS' => (string) the URL where Docxpresso should
     *       fetch for some external CSS file.
     *      'requestExternalJS' => (string) the URL where Docxpresso should
     *       fetch for some external JS file.
     *      'responseDataURI' => (string) the URL where Docxpresso should
     *       forward the user data. Default value is an empty string.
     *      'processingDataURI' => (string) the URL where Docxpresso should
     *       postprocess doc data. Default value is an empty string.
     *      'responseURL' => (string) the URL where Docxpresso should redirect
     *       the end user after saving the data. Default value is an empty 
     *       string. 
     *      'documentName' => (string) the name we want to give to the generated
     *       document. Default value is empty and in that case Docxpresso
     *       will use the default template name. 
     *       string.
     *      'domain' => (string) the URL doing the request. Default value is an 
     *       empty string.
     *      'prefix' => (string) a prefix that will limit enduser edition to
     *       only the field variables that start by that prefix. You can use
     *       a comma separated list to select more than one prefix. Default value 
     *       is an empty string.
     *      'editableVars' => (string) a comma separated list of var names
     *       to restrict the edition to that set. Default value is an empty 
     *       string.
     *      'blockVars' => (string) a comma separated list of var names
     *       which edition is expicitly blocked. Default value is an empty 
     *       string.
     *      'enforceValidation' => (boolean) if true the user will not be able
     *       to send data until all variable fields are validated. Default value
     *       is false.
     *      'language' => (string) if set will change the default interface
     *       language. Currently available values are: default, en, es
     *      'GDPR' => (boolean) if true the end user will be prompted to check
     *       the privacy policy (it only applies if it is also globally defined)
     *      'phone' => (string) with standard international format +12121112222.
     *       If given the end user will be first prompted to introduce an OTP
     *       sent to that phone.
     *      'OTPmessage' => (string) this only applies if we use a phone to
     *       request an OTP
     *      'varData' => additional JSON data we would like to preload into the 
     *       document
     *      'continueLink' => (boolean) if true the end user will be prompted
     *       to introduce an email where he can continue later the edition
     *      'continueLinkEmail' => if continueLink is set to true and this 
     *       option is given it will be shown as the default email to receive
     *       the continue link.
     *      'blockDocument' => (boolean) if true the end user will be asked if
     *       the document should be blocked from further edition.
     *      'history' => (boolean) if true and there is a token (or history data
     *       is provided from external sources) the user will be able to
     *       visualize the edition history of the document.
     *      'keepAlive' => (boolean) if true the generated link is alived and
     *       allows for multiple editions of the same document.
     *      'trackID' => (string) if given it will be used whenever the 
     *       keepAlive is set to true, otherwise it will be autogenerated.
     *      'loadContactData' => (integer) id of the contact whose data we want
     *       to load into the template.
     *      'requestVars' => (array) list of variables that should be sent in
     *       the query parameter of the responseURL
     *      'plugin' => (boolean) if true the petition may directly come from
     *       a plugin user so further security checks may be carried out to test
     *       for data coherence and permissions.
     *      'enableRejection' => (boolean) if true the user will be offered a
     *       button to directly 'reject' the document wuithout fulfilling it.
     *      'livePreview' => (boolean) this property only applies to web forms, 
     *       if true a live preview of the generated document will be offered.
     *      'client' => (integer) cliend id. Only for multi tenant instances.
     * @param array $mailer with the following keys and values
     *      'email' => (string) the email address where to send the edition link
     *      'logo' => (string) URL where to fetch the logo. If not given the 
     *       default Docxpresso instance logo will be used
     *      'subject' => (string) email subject. If not given the template title
     *       will be used
     *      'body' => (string) HTML text. If not given the template description
     *       will be used.
     *      'callToAction' => (string) text of the link button. If not given
     *       the default text will be used.
     *      'emailTemplate' => (string) path to the required email template. 
     *       If not given the default template will be used.
     *      'footer' => (string) HTML text. If not given the default footer
     *       will be used.
     * 
     * @return string
     * @throws UnsupportedEncodingException
     * @access public
     */
    public String sendEditLinkByEmail( Map<String, Object> data,  Map<String, Object> mailer) throws UnsupportedEncodingException
    { 
        String link = Utils.previewDocument(data);
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/send_email_edit/" + data.get("template");
        
        Map<String, Object> options = new HashMap<>();
        options.put("link", link);
       
        if (mailer.get("email")!= null) {
            options.put("email", mailer.get("email"));
        }
        if (mailer.get("logo")!= null) {
            options.put("logo", mailer.get("logo"));
        }
        if (mailer.get("subject")!= null) {
            options.put("subject", mailer.get("subject"));
        }
        if (mailer.get("body")!= null) {
            options.put("body", mailer.get("body"));
        }
        if (mailer.get("callToAction")!= null) {
            options.put("callToAction", mailer.get("callToAction"));
        }
        if (mailer.get("emailTemplate")!= null) {
            options.put("emailTemplate", mailer.get("emailTemplate"));
        }
        if (mailer.get("footer")!= null) {
            options.put("footer", mailer.get("footer"));
        }
        
        String opt = Utils.base64_encode_url_safe(Json.encode(options));
        
        return Utils._returnLink(url, data.get("template").toString(), opt);    
    }


    /**
     * Returns a link to download all document validations in JSON(P)  
     * format  for a given template id from the associated Docxpresso 
     * SERVER installation
     * 
     * @param array $data with the following keys and values
     *      'id' => (int) the id of the template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'status' => (boolean) if true only acepted documents will be listed
     *       and if false only rejected documents will be listed. Default value
     *       is empty
     *      'enduserid' => (string) the end user id of a particular revision.
     *       Default value is empty.
     *      'period' => (string) if given will overwrite the given startDate and
     *       enddate parameters. The possible values are: today, 
     *       1week (last week), 1month (last month), 3month (last quarter),
     *       year (last year). The default value is empty
     *      'startDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened after it. Default value is an empty 
     *       string.
     *      'endDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened before it. Default value is an empty 
     *       string.
     *      'firstResult' => (int) query offset. Default value is 0; 
     *      'maxResults' => (int) maximum number of results. Beware that
     *       each installation may have its own limits to this number
     *      (usually 100)
     *       Default value is empty and Docxpresso default will be used.
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String validationsByTemplate(Map<String, Object> data, String... varCallback )
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/validations_by_template/" + data.get("id");
		
        if (callback!= null) {
                url += "/" + callback;
        }

        //we build and options object with the search filters
        Map<String, Object> options = new HashMap<>();
        if (data.get("status") != null && data.get("status").toString().equals("true")) {
            options.put("status", 1);
        } else if (data.get("status") != null && !data.get("status").toString().equals("true")) {  
            options.put("status", 0);
        } else {
            options.put("status", 2);
        }
        if (data.get("enduserid")!= null) {
            options.put("enduserid", data.get("enduserid"));
        }
        //dates must be in the format 2016-01-30
        if (data.get("startDate")!= null) {
            options.put("startDate", data.get("startDate"));
        }
        if (data.get("endDate")!= null) {
            options.put("endDate", data.get("endDate"));
        }
        if (data.get("period")!= null) {
            options.put("period", data.get("period"));
        }
        if (data.get("firstResult")!= null) {
            options.put("firstResult", data.get("firstResult"));
        }
        if (data.get("maxResults")!= null) {
            options.put("maxResults", data.get("maxResults"));
        }
        if (data.get("sort")!= null) {
            options.put("sort", data.get("sort"));
        }
        if (data.get("order")!= null) {
            options.put("order", data.get("order"));
        }

        String opt = Utils.base64_encode_url_safe(Json.encode(options));
		
        
        return Utils._returnLink(url, data.get("id").toString(), opt);   
    }
 
 /**
     * Generates a one time link to simply view a document within the
     * Docxpresso interface with no action associated
     * 
     * @param array $data with the following keys and values
     *      'template' => (int) the id of the requested document template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'token' => (string) a unique identifier of a previous use. This 
     *       value must correspond to a valid usage token.
     *      'requestDataURI' => (string) the URL where Docxpresso should fetch
     *       external data. Default value is an empty string.
     *      'varData' => the JSON data we would like to use to generate the 
     *       document.
     *      'language' => (string) if set will change the default interface
     *       language. Currently available values are: default, en, es.
     *      'toolBar' => (boolean) if false the fixed bar at the top will be 
     *       removed.
     *      'permanent' => (boolean) if set the link will never expire. Default 
     *       value is false.
     *      'history' => (boolean) if true the user will be able to
     *       visualize the edition history of the document.
     *      
     * @return string
     * @access public
     */
    public String viewDocument(Map<String, Object> data)
    {     
        if (data.get("token") != null){
            data.put("token", 0);
        }
        String url = _options.get("docxpressoInstallation") + "/documents/validate/view/";
        url += data.get("template") + "/" + data.get("token");
        Map<String, Object> options = new HashMap<>();
        if (data.get("requestDataURI")!= null) {
            Map<String, Object> dURI = new HashMap<>();
            dURI.put("URL", data.get("requestDataURI"));
            dURI.put("requestData", "view");
            options.put("requestDataURI", Json.encode(dURI)); 
        }
        if (data.get("varData") != null) {
            options.put("data",data.get("varData"));
        } 
        if (data.get("language") != null) {
            options.put("locale",data.get("language"));
        } 
        if (data.get("toolBar") != null) {
            options.put("toolBar",data.get("toolBar"));
        } 
        if (data.get("permanent") != null) {
            options.put("permanent",data.get("permanent"));
        } 
        if (data.get("history") != null) {
            options.put("history",1);
        } 
        
        String opt = Utils.base64_encode_url_safe(Json.encode(options));
		
        //in this case we need to concatenate id and token so the apikey
        //can not be reused for methods that allowed to use twice the
        //apikey
        String id = data.get("template").toString() + data.get("token") + "view";
        return Utils._returnLink(url, id, opt);    
    }

    /**
     * Generates a one time link to simply view a document history within the
     * Docxpresso interface with no action associated
     * 
     * @param array $data with the following keys and values
     *      'template' => (int) the id of the requested document template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'token' => (string) a unique identifier of a previous use. This 
     *       value must correspond to a valid usage token.
     *      'requestDataURI' => (string) the URL where Docxpresso should fetch
     *       external data with history data. Default value is an empty string.
     *       if given it will override the token.
     *      'varData' => the JSON data we would like to use to generate the 
     *       history associated with that document. If given will override the
     *       token and requestDataURI.
     *      'language' => (string) if set will change the default interface
     *       language. Currently available values are: default, en, es.
     *      
     * @return string
     * @access public
     */
    public String viewHistoryDocument(Map<String, Object>  data)
    {     
        if (data.get("token")!= null ){
            data.put("token", 0);
        }
        String url = _options.get("docxpressoInstallation") + "/documents/history/view/";
        url += data.get("template") + "/" + data.get("token");
        Map<String, Object> options = new HashMap<>();
        if (data.get("requestDataURI")!= null) {
            Map<String, Object> dURI = new HashMap<>();
            dURI.put("URL", data.get("requestDataURI"));
            dURI.put("requestData", "preview");
            options.put("requestDataURI", Json.encode(dURI)); 
        }
        if (data.get("varData")!=null) {
            options.put("data", data.get("varData"));
        } 
        if (data.get("language")!=null) {
            options.put("locale", data.get("language"));
        } 
        
        String opt = Utils.base64_encode_url_safe(Json.encode(options));
        
        //in this case we need to concatenate id and token so the apikey
        //can not be reused for methods that allowed to use twice the
        //apikey
        
        String id = data.get("template").toString() + data.get("token") + "view";
        return Utils._returnLink(url, id, opt);     
    }

    /**
     * Generates a one time link to regenerate a "full· document package" in zip
     * format (document + attachments) from the associated
     * Docxpresso SERVER installation
     * 
     * @param array $data with the following keys and values
     *      'id' => (int) the id of the corresponding template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'token' => (string) the token of the requested usage.
     *      'identifier' => (string) if given the token will be ignored and
     *       the returned document will be the last document retrieved with that
     *       identifier.
     *      'onlyDocument' => (boolean) if true only downloads the main document
     *       ignoring any potential attachment. Default value false.
     *      'documentName' => (string) the name we want to give to the generated
     *       document (it should include the extensions: .odt, .pdf, .doc, 
     *       .doc(legacy), .docx or .rtf). The default values is document.odt
     * 
     * @return string
     * @access public
     */
    public String  regenerateDocument(Map<String, Object> data)
    {    
        String url = _options.get("docxpressoInstallation") + "/documents/regenerateDocument/" + data.get("id");
        
        Map<String, Object> options = new HashMap<>();
        if (data.get("identifier") != null){
            options.put("identifier", data.get("identifier"));
        } else {
            options.put("token", data.get("token"));
        }
        if (data.get("onlyDocument") != null){
            options.put("onlyDocument", data.get("onlyDocument"));
        }
        if (data.get("documentName") != null){
            options.put("documentName", data.get("documentName"));
        }
        
        String opt = Utils.base64_encode_url_safe(Json.encode(options));

        return Utils._returnLink(url, data.get("id").toString(), opt); 
    }

     /**
     * Generates a one time link to generate a document in the associated
     * Docxpresso SERVER interface
     * 
     * NOTE: if data is loaded from various sources it will be loaded with the 
     * folllowing priority: varData, requestDataURI, token
     * 
     * @param array $data with the following keys and values
     *      'template' => (int) the id of the requested document template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'identifier' => (string) optional var name that we may pass to help 
     *       identify that particular usage. Default value is an empty string 
     *      'reference' => (string) an optional string we may pass to help 
     *       identify that particular usage. Default value is an empty string
     *      'requestDataURI' => (string) the URL where Docxpresso should fetch
     *       external data. Default value is an empty string.
     *      'documentName' => (string) the name we want to give to the generated
     *       document (it should include the extensions: .odt, .pdf, .doc, 
     *       .doc(legacy), .docx or .rtf). The default value is document.odt
     *      'varData' => the JSON data we would like to use to generate the 
     *       document.
     *      'token' => (string) a unique identifier of a previous use. If given
     *       the data associated with the token will be preloaded into 
     *       the document.
     *      'display' => (string) it can be 'document' (default) or 'form'. 
     *       This is only used for the generation of continue links
     *      'response' => (string) it can be 'download'(default) if the document
     *       is to be directly downloadable from the browser or 'json' if we want
     *       to get the document as base64 encoded together with the usage id
     *       and token
     *      'callback' => it only spplies to json responses and sets the name
     *       of the callback function for JSONP responses.
     *      'client' => (integer) cliend id. Only for multi tenant instances. 
     * @return string
     * @access public
     */
    public String requestDocument(Map<String, Object> data)
    {    
        String url = _options.get("docxpressoInstallation") + "/documents/requestDocument/" + data.get("template");
        
        Map<String, Object> options = new HashMap<>();
        if (data.get("documentName")!=null) {
            options.put("name", data.get("documentName"));
        } else {
            options.put("name", "document.odt");
        }
        if (data.get("varData")!=null) {
            options.put("data", data.get("varData"));
        } else {
            options.put("data", "{}");
        }
        if (data.get("display")!=null) {
            options.put("display", data.get("display"));
        } else {
            options.put("display", "document");
        }
        if (data.get("requestDataURI")!= null) {
            Map<String, Object> dURI = new HashMap<>();
            dURI.put("URL", data.get("requestDataURI"));
            dURI.put("requestData", "request");
            options.put("requestDataURI", Json.encode(dURI)); 
        }
        if (data.get("token")!=null) {
            options.put("token", data.get("token"));
        }
        if (data.get("identifier")!=null) {
            options.put("identifier", data.get("identifier"));
        }
        if (data.get("client")!=null) {
            options.put("client", data.get("client"));
        }
        if (data.get("reference")!=null) {
            options.put("reference", data.get("reference"));
        }
        if (data.get("response")!=null) {
            options.put("response", data.get("response"));
        }
        
        String opt = Utils.base64_encode_url_safe(Json.encode(options));

        return Utils._returnLink(url, data.get("template").toString(), opt);  
    }
    
    
     /**
     * Generates a one time link to sign a document generated with Docxpresso
     * 
     * @param array $data $data with the following keys and values
     *      'usageId' => (int) the id of the corresponding usage.
     *       This value is compulsory and must correspond to a valid document
     *      'token' => (string) the token of the given usage for further
     *       security. 
     *      'provider' => (string) it can be vidSigner, Lleida.net or NodalBlock
     *      'signers' => (array) an array of arrays where the second array 
     *       includes the following key and values (some may be optional
     *       depending on the provider and how the signature is parametrized):
     *          'name': (string) signer's name
     *          'id': (string) signer's id
     *          'email': (string) signer's email
     *          'phone': (string) signer's phone
     *       
     * @return string
     * @access public
     */
    public String requestSignature(Map<String, Object> data)
    {
        return "TO BE DONE";
    }

    /**
     * Returns a link to download all document revisions in JSON(P)  
     * format  for a given template id from the associated Docxpresso 
     * SERVER installation
     * 
     * @param array $data with the following keys and values
     *      'id' => (int) the id of the template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'status' => (boolean) if true only acepted documents will be listed
     *       and if false only rejected documents will be listed. Default value
     *       is empty
     *      'enduserid' => (string) the end user id of a particular revision.
     *       Default value is empty.
     *      'period' => (string) if given will overwrite the given startDate and
     *       enddate parameters. The possible values are: today, 
     *       1week (last week), 1month (last month), 3month (last quarter),
     *       year (last year). The default value is empty
     *      'startDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened after it. Default value is an empty 
     *       string.
     *      'endDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened before it. Default value is an empty 
     *       string.
     *      'firstResult' => (int) query offset. Default value is 0; 
     *      'maxResults' => (int) maximum number of results. Beware that
     *       each installation may have its own limits to this number
     *      (usually 100)
     *       Default value is empty and Docxpresso default will be used.
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String revisionsByTemplate(Map<String,Object>data, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/revisions_by_template/" + data.get("id");
		
        if (callback != null) {
                url += "/" + callback;
        }

        //we build and options object with the search filters
        Map<String, Object> options = new HashMap<>();
        if (data.get("status") != null && data.get("status").toString().equals("true")) { 
            options.put("status", 1);
        } else if (data.get("status") != null && data.get("status").toString().equals("false")) {
            options.put("status", 0);
        } else {  
            options.put("status", 2);
        }
        if (data.get("enduserid")!=null) {
            options.put("enduserid", data.get("enduserid"));
        }
        //dates must be in the format 2016-01-30
        if (data.get("startDate")!=null) {
            options.put("startDate", data.get("startDate"));
        }
        if (data.get("endDate")!=null) {
            options.put("endDate", data.get("endDate"));
        }
        if (data.get("period")!=null) {
            options.put("period", data.get("period"));
        }
        if (data.get("firstResult")!=null) {
            options.put("firstResult", data.get("firstResult"));
        }
        if (data.get("maxResults")!=null) {
            options.put("maxResults", data.get("maxResults"));
        }
        if (data.get("sort")!=null) {
            options.put("sort", data.get("sort"));
        }
        if (data.get("order")!=null) {
            options.put("order", data.get("order"));
        }
        
        String opt = Utils.base64_encode_url_safe(Json.encode(options));
		
        return Utils._returnLink(url, data.get("id").toString(), opt);
    }

    /**
     * Generates a one time link to download an attachment from the associated
     * Docxpresso SERVER installation
     * 
     * @param array $data $data with the following keys and values
     *      'usageId' => (int) the id of the corresponding usage.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'name' => (string) the name of the attachment file we want to
     *       download. It should correspond to the name given in the Docxpresso
     *       SERVER processing interface.
     *      'token' => (string) the token of the given usage for further
     *       security. 
     * @return string
     * @access public
     */
    public String downloadAttachment(Map<String, Object> data)
    {    
        String url = _options.get("docxpressoInstallation") + "/documents/getAttachment/" + data.get("usageId");
		
        
        //Long uniqid = Utils.generate_uniqid();
        //Long timestamp = Math.round(Math.floor( new Date().getTime()/1000));

        Map<String, Object> options = new HashMap<>();
        options.put("name", data.get("name"));
        options.put("token", data.get("token"));
        
        String opt = Utils.base64_encode_url_safe(Json.encode(options));
      
        return Utils._returnLink(url, data.get("usageId").toString(), opt);     
    }

    /**
     * Generates a one time link to download a "full· document package" in zip
     * format (document + attachments) from the associated
     * Docxpresso SERVER installation
     * 
     * @param array $data $data with the following keys and values
     *      'id' => (int) the id of the corresponding template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'token' => (string) the token of the requested usage.
     *      'identifier' => (string) if given the token will be ignored and
     *       the returned docuemnt will be the last document retrieved with that
     *       identifier.
     *      'onlyDocument' => (boolean) if true only downloads the main document
     *       ignoring any potential attachment. Default value false.
     *       'plugin' => (boolean) if true the petition may directly come from
     *       a plugin user so further security checks may be carried out to test
     *       for data coherence and permissions.
     * 
     * @return string
     * @access public
     */
    public String downloadDocument(Map<String, Object>data)
    {    
        String url = _options.get("docxpressoInstallation") + "/documents/getFullDocumentation/" + data.get("id");

        Map<String, Object> options = new HashMap<>();
        if (data.get("identifier")!=null){
            options.put("identifier", data.get("identifier"));
        } else {
            options.put("token", data.get("token"));
        }
        if (data.get("onlyDocument")!=null){
            options.put("onlyDocument", data.get("onlyDocument"));
        }
        if (data.get("plugin")!=null){
            options.put("plugin", 1);
        }
        
        String opt = Utils.base64_encode_url_safe(Json.encode(options));
       
        return Utils._returnLink(url, data.get("id").toString(), opt);
    }

    /**
     * Generates a one time link to get a JSON with all info associated with
     * a forwarded document including the document base64 encoded(it may be
     * a zip if the document has attachments) from the associated
     * Docxpresso SERVER installation
     * 
     * @param array $data $data with the following keys and values
     *      'id' => (int) the id of the corresponding forwarded document
     *      'processed' => (int) if given will set the processed flag this
     *      value. For example you may set its value to 1 in order to restrict
     *      future searches. 
     * @return string
     * @access public
     */
    public String fetchForwardedDocument(Map<String,Object> data)
    {       
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/fetch_forwarded_document/" + data.get("id");
        Map<String, Object> options = new HashMap<>();
        if (data.get("processed")!=null){
            options.put("processed", data.get("processed"));
        }
        String opt = Utils.base64_encode_url_safe(Json.encode(options));
        return Utils._returnLink(url, data.get("id").toString(), opt);
    }

    /**
     * Generates a one time link to get all annex document data: thumbnail, 
     * base64 encoded template odt file, etcetera.
     * 
     * @param integer $token the token of the requested annex
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String getAnnexData(String token,String... varCallback )
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/get_annex/" + token;
		
        if (callback != null) {
            url += "/" + callback;
        }
		
        return Utils._returnLink(url);
    }

    /**
     * Generates a one time link to get all document template data: Docxpresso
     *  data, thumbnail, base64 encoded template odt file, etcetera.
     * 
     * @param integer $id the id of the required template
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String getTemplateData(Integer id, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/get_template/" + id;

        if (callback != null) {
            url += "/" + callback;
        }
		
        return Utils._returnLink(url, id.toString());
    }

    /**
     * Generates a one time link to get just a template thumbnail
     * 
     * @param integer $id the id of the required template
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String getTemplateThumbnail(Integer id, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/get_thumbnail/" + id;

        if (callback != null) {
            url += "/" + callback;
        }
		
        return Utils._returnLink(url, id.toString());
    }

    /**
     * Generates a one time link to get the signatures parametrization data
     * from the template
     * 
     * @param integer $id the id of the required template
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String getTemplateSignatureData(Integer id, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/get_template_signature_data/" + id;

        if (callback != null) {
            url += "/" + callback;
        }
		
        return Utils._returnLink(url, id.toString());
    }

     /**
     * Get current usage for administrative purposes
     * 
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return array
     * @access public
     */
    public String  getUsageCurrent(String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/get_usage_current";

        if (callback != null) {
            url += "/" + callback;
        }
		
        return Utils._returnLink(url);
    }

    /**
     * Get usage history by year/month for administrative purposes
     * 
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String getUsageHistory(String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/get_usage_history";

        if (callback != null) {
            url += "/" + callback;
        }
		
        return Utils._returnLink(url);
    }
/**
     * Modifies the template configuration: variables and groups. 
     * WARNING: beware that in order to modify the group properties you need
     * to know the group ids that can be retrieved with the getTemplateData
     * method or generated as follows: in order to generate the id one need to
     * know the name of all variables inclued in the group and generate the
     * md5 hash obtained from concatening with commas all those variables
     * following their order of appearance in the corresponding template.
     * For example, the group id of a table with two variables named product and
     * price will be md5('product,price')
     * 
     * @param array data an array of arrays with the folowing key values:
     *      'id': the id of the required template
     *      'settings': an array of arrays with the following key value pairs:
     *         'numberFormat': can bw ".;" or ",."
     *         'outputComments' (boolean)
     *         'outputFormat': an array with the possible, non-exclusive, values
     *          ["odt", "pdf", "doc", "docx", "doc-legacy", "rtf"]
     *     'variables': an array of arrays with the following key value pairs:
     *         'variable name': an array with the following key value pairs:
     *             'scope': can be document, form or both (default value)
     *             'label' (text)
     *             'tip' (text)
     *             'comment' (HTML text)
     *             'type': it can be text, options, date or phone.
     *             'richtext' (boolean) only applies if the type is text.
     *             'choice': dropdown, checkbox or radio. Only applies if the
     *               type is options.
     *             'options': ";" separated list of values. Only applies if the
     *              type takes the "options" value
     *             'compulsory'(boolean)
     *             'editable' (boolean)
     *             'global' (boolean)
     *             'confirm' (boolean)
     *             'validation' (string) validation name (only relevant to
     *              identify the validation in the web edition interface)
     *             'regex': regular expression used to validate this field
     *     'groups': an array of arrays with the following key value pairs:
     *          'group id': an array with the following key value pairs:
     *             'active' (boolean) if true (default value) this group
     *              is clonable
     *             'display': can be show (default) or hide
     *             'print': can be print (default), unprint (only visible in
     *              the browser) or unbrowsable (only printed and not visible
     *              in the browser)
     *             'toggleWith' (text)
     *             'toggleValues' (text)
     *  
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String modifyTemplateData(Map<String, Object> data, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/modify_template/" + data.get("id");

        if (callback != null) {
            url += "/" + callback;
        }
        
        Map<String, Object> options = new HashMap<>();
        options.put("id", data.get("id"));
        if (data.get("settings")!=null){
            options.put("settings", data.get("settings"));
        } else {
            options.put("settings", "{}");
        }
        if (data.get("variables")!= null){
            options.put("variables", data.get("variables"));
        } else {
            options.put("variables","{}");
        }
        if (data.get("groups")!=null){
            options.put("groups", data.get("groups"));
        } else {
            options.put("groups", "{}");
        }
		
        String opt = Utils.base64_encode_url_safe(Json.encode(options));

        return Utils._returnLink(url, data.get("id").toString(), opt);
    }

    /**
     * Allows to remotely authenticate from any other application into the
     * associated Docxpresso SERVER installation
     * 
     * @param array $data $data with the following keys and values
     *      'email' => (string) the email of the user we want to log in.
     *       This value is compulsory and must correspond to a valid registered
     *       user email.
     *      'url' => (string) target url where the user should be redirected
     *       after being authenticated
     *      'referer' => (string) domain origin of the petition
     * @return string
     * @access public
     */
    public String accessByTokenAction(Map<String, Object> data)
    {    
        String url = _options.get("docxpressoInstallation") + "/users/accessByToken";

        Map<String, Object> options = new HashMap<>();
        options.put("email", data.get("email"));
        options.put("url", data.get("url"));

        if (data.get("referer")!=null){
            options.put("referer", data.get("referer"));
        }
        String opt = Utils.base64_encode_url_safe(Json.encode(options));

        return Utils._returnLink(url, null, opt);   
    }

    /**
     * Allows to remotely create an user
     * 
     * @param array $data $data with the following keys and values
     *      'username' => (string) alias to be used within Docxpresso.
     *       This value is compulsory and can not coincide with the username 
     *       of an already registered user.
     *      'email' => (string) the email of the user we want to create.
     *       This value is compulsory and can not coincide with the email of an
     *       existing user.
     *      'password' => (string) the password of the user we want to create.
     *       This value is compulsory and must be safe.
     *      'name' => (string) full user name (compulsory)
     *      'role' => (string) it must take one of the following values: admin,
     *       editor, user, external. Default value is user.
     *      'position' => (string) user position (optional)
     *      'phone' => (string) user phone (optional)
     *      'description' => (string) short user description (optional)
     * @return string
     * @access public
     */
    public String createUser(Map<String, Object>data)
    {    
        String url = _options.get("docxpressoInstallation") + "/users/createRemoteUser";

        Map<String, Object> options = new HashMap<>();
        options.put("email", data.get("email"));
        options.put("username", data.get("username"));
        options.put("password", data.get("password"));
        options.put("name", data.get("name"));

        if (data.get("role") == "admin"){
            options.put("role", 3);
        } else if (data.get("role") == "editor"){ 
            options.put("role", 2);
        } else if (data.get("role") == "user"){
            options.put("role", 1);
        } else if (data.get("role") == "external"){
            options.put("role", 0);
        }
        if (data.get("position")!=null){
            options.put("position", data.get("position"));
        } else {
            options.put("position", " ");
        }
        if (data.get("phone")!=null){
            options.put("phone", data.get("phone"));
        } else {
            options.put("phone", " ");
        }
        if (data.get("description")!=null){
            options.put("description", data.get("description"));
        } else {
            options.put("description", "");
        }

        String opt = Utils.base64_encode_url_safe(Json.encode(options));

        return Utils._returnLink(url, null, opt);    
    }

    /**
     * Create a workflow
     * 
     * @param array $data with the following keys and values
     *      'template' => (int) the id of the requested document template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'description' => (string) short (HTML) description of the workflow
     *       to be used in automated emails.
     *      'token' => (string) a unique identifier of a previous use. If given
     *       the data associated with the token will be preloaded into 
     *       the document. The default value is NULL.
     *      'ordered' => (boolean) if true (default value) the workflow will be
     *       an ordered workflow.
     *      'display' => (string) it can be 'document' (default) or 'form'.
     *      $steps => (array) the steps should be an array of arrays each of 
     *      them with the following keys and values:
     *          'username' => (string) the name of the user associated with that
     *           step.
     *          'useremail' => (string) the email of the user associated with
     *           that step.
     *          'action' => (string) the associated action. It can take the
     *           following values: 'edit' (default value) or 'validate'.
     *          'rejectionAction' => (string) the action to be taken if a 
     *           validation is rejected. It can take the following values:
     *           'stepBack' (default value) or 'complete'.
     *          'notify' => (boolean) if true (default value) the user will be
     *           notified by email.
     *          'send' => (boolean) if true (default value) the user will
     *           receive a copy of the document upon completion of the workflow.
     *          'prefix' => (string) a prefix that will limit enduser edition to
     *           only the field variables that start by that prefix. You can use
     *           a comma separated list to select more than one prefix. Default 
     *           value is an empty string.
     *          'editableVars' => (string) a comma separated list of var names
     *           to restrict the edition to that set. Default value is an empty 
     *           string.
     *     
     * @return string
     * @access public
     */
    

    public String createWorkflow(Map<String, Object> data, Map<String, Object> dataSteps,String... varCallback) {
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/documents/workflow/create_remote_workflow/" + data.get("template");
        if (callback != null) {
            url += "/" + callback;
        }
        Map<String, Object> options = new HashMap<>();
        if (data.get("token") != null) {
            options.put("token", data.get("token"));
        }
        if (data.get("display") != null) {
            options.put("display", data.get("display"));
        }
        Map<String, Object> workflow = new HashMap<>();
        boolean ordered = true;
        if (data.get("ordered") != null && !(Boolean)data.get("ordered")) {
            ordered = false;
        }
        workflow.put("ordered", ordered);
        if (data.get("description") != null) {
            workflow.put("description", data.get("description"));
        } else {
            workflow.put("description", "");
        }
        
        
        Map<Number, Object> steps = new HashMap<>();
        
        Integer numSteps = dataSteps.size();

        for (int j = 0; j < numSteps; j++){
            Map<String, Object> step = new HashMap<>();
            step.put("completed", 0); 
            if(step.get("notify")!= null && step.get("notify").toString().equals("true")){
                step.put("notify",0);
            } else {
                step.put("notify",1);
            } 
            if (step.get("send")!= null && step.get("send").toString().equals("true")) {
                step.put("send",0);
            } else {
                step.put("send",1);
            }
            if (step.get("action")!= null && step.get("action").toString().equals("true")) {
                step.put("actionType","validate");
            } else {
                step.put("actionType","edit");
            }
            if (step.get("rejectionAction")!= null && step.get("rejectionAction").toString().equals("true")) {
                step.put("rejectionAction","completedWF");
            } else {
                step.put("rejectionAction","stepBack");
            }
            if (step.get("prefix")!= null) {
                step.put("prefix",dataSteps.get("prefix"));
            } else { 
                step.put("prefix","");
            }
            if (step.get("editableVars")!= null) {
                step.put("editableVars",dataSteps.get("editableVars"));
            } else {
                step.put("editableVars","");
            }
            step.put("useremail",dataSteps.get("useremail"));
            step.put("username",dataSteps.get("username"));
            steps.put(j,step);

        }

        workflow.put("steps", steps);
        options.put("workflow", workflow);
    
        String opt = Utils.base64_encode_url_safe(Json.encode(options.toString()));

        return Utils._returnLink(url, data.get("template").toString(), opt);    
    }
    
    /**
     * Returns a link to recover the info about a certain workflow by id
     * 
     * @param integer $id the workflow id
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public 
     */
    public String getWorkflowData(Integer id, String... varCallback)
    {      
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/workflowData/" + id;
        
        if (callback != null) {
            url += "/" + callback;
        }

        return Utils._returnLink(url, id.toString());    
       
        
    }

 /**
     * Returns a link to list of categories in JSON(P) format from the associated
     * Docxpresso SERVER installation
     * 
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String listCategories(String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/categories";
        
        if (callback != null) {
            url += "/" + callback;  
        }
       
        return Utils._returnLink(url);
    }

    /**
     * Returns a link to list of documents in a given category in JSON(P) format
     * from the associated Docxpresso SERVER installation
     * 
     * @param integer $category the corresponding category id.
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @param boolean $published if true only "published" templates will be
     * available through the request.
     * @param string $access it can be "all" (deafult value), "public" for only
     * documents declared like public or a "username" to filter by permissions
     * @return string
     * @access public
     */
    public String  documentsByCategory(Integer category,Map<String, Object> preVariables/*callback = '', published = 0, access = 'all'*/)
    {    
        
        String callback = preVariables.get("callback") != null ? preVariables.get("callback").toString() : "";
        String published = preVariables.get("published") != null ? preVariables.get("published").toString() : "false";
        String access = preVariables.get("access") != null ? preVariables.get("access").toString() : "all";

        String url = _options.get("docxpressoInstallation") + "/RESTservices/predefined/documents_by_category/" + category;

        if (callback != null) {
            url += "/" + callback;
        } else {
            url += "/NULL";
        }

        if (published.equals("true")) {
            url += "/1";
        } else {
            url += "/0";
        }
        
        if (access != null) {
            try {
                url += "/" + URLEncoder.encode(access ,"UTF-8"); 
              } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
              }
            
        }
		
        return Utils._returnLink(url);
      }

      

    /**
     * Allows to change the password associated with a user email.
     * 
     * @param string $email user unique email identifier.
     * @param string $password new password. It should be, at least 12 chars long
     * and contain at least an uppercase letter, a lowercase letter, a number
     * and a non-standard char: !,%,&,@,#,$,^,*,?,_,~
     * @param boolean $notify set it to true (default value) if you want to
     * the user of the password change
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */

    public String modifyPassword(String email, String password,Map<String, Object> array)
    {    
         
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/modify_password";

        if(array.get("callback") != null){
            url += '/' + array.get("callback").toString();
        }
        Map<Object, Object> options = new HashMap<>();
        options.put("email", email);
        options.put("password", password);
        if(array.get("notify") != null){
            options.put("notify", array.get("notify"));
        } else {
            options.put("notify", true);
        }
        String jsonStrOptions = JSONValue.toJSONString(options);
        String opt = Utils.base64_encode_url_safe(jsonStrOptions);
         
        return Utils._returnLink(url, null, opt);
    }

    /**
     * Allows to modify the configuration of Signature Providers. If the
     * requested signature provider does not exist and it belongs to one of
     * the current available ones the corresponding entry will be created.
     * 
     * @param string $provider the name of the signature provider. Currently
     * the only available ones are vidSigner, lleida.net or nodalblock
     * @param string $config base64 encoded config JSON.
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String modifySignatureProvider(String provider,String config,String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/modify_signature_providers/" + provider;

        if (callback != null) {
            url += '/' + callback;
        }
        Map<Object, Object> options = new HashMap<>();
	    options.put("custom", config);
        

        String opt = Utils.base64_encode_url_safe(Json.encode(options));
        return Utils._returnLink(url, null, opt);
    }
     
    /**
     * Allows to delete a current signature provider.
     * 
     * @param string $provider the name of the signature provider. Currently
     * the only available one is vidSigner
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @param boolean $published if true only "published" templates will be
     * available through the request.
     * @return string
     * @access public
     */

    public String deleteSignatureProvider(String provider, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/delete_signature_providers/" + provider;
        

        return Utils._returnLink(url); 
    }

    /**
     * List all Signature Providers
     * 
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @param boolean $published if true only "published" templates will be
     * available through the request.
     * @return string
     * @access public
     */
    public String listSignatureProviders(String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/list_signature_providers";

        if (callback!=null) {
            url += "/" + callback;
        }
		
        return Utils._returnLink(url);
    }

    /**
     * Returns a link to download the whole document (sub)tree in JSON(P) format  
     * from the associated Docxpresso SERVER installation
     * 
     * @param mixed $rootCategory the corresponding category id from which
     * we want to build the document tree. Default value is 'root' that corresponds
     * with the "root category"
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @param boolean $published if true only "published" templates will be
     * available through the request.
     * @param string $access it can be "all" (deafult value), "public" for only
     * documents declared like public or a "username" to filter by permissions
     * @return string
     * @access public
     */
    public String documentTree(Map<String, Object> array)
    {    
        Object rootCategory = array.get("rootCategory") != null ? array.get("rootCategory") : "root";
        Object callback = array.get("callback") != null ? array.get("callback") : null;
        Object published = array.get("published") != null ? array.get("published") : null;
        Object access = array.get("access") != null ? array.get("access") : "all";

        if (rootCategory == "root") {
            rootCategory = 1;
        }
        
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/document_tree/" + rootCategory;

        if (callback != null) {
            url += "/" + callback;
        } else {
            url += "/NULL";
        }

        if (published != null ) {
            url += "/1";
        } else {
            url += "/0";
        }
        
        if (access != null) {
            try {
                url += "/" + URLEncoder.encode(access.toString() ,"UTF-8"); 
              } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
              }
            
        }
        

        return Utils._returnLink(url);
    }

     /**
     * Returns a link to download the whole category (sub)tree in JSON(P) format  
     * from the associated Docxpresso SERVER installation. If you need to include
     * documents use the documentTree method instead.
     * 
     * @param mixed $rootCategory the corresponding category id from which
     * we want to build the document tree. Default value is 'root' that corresponds
     * with the "root category"
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String categoryTree(Map<String, Object> array)
    {    
        Object rootCategory = array.get("rootCategory") != null ? array.get("rootCategory") : "root";
        Object callback = array.get("callback") != null ? array.get("callback") : null;

        if (rootCategory == "root") {
            rootCategory = 1;
        }
        
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/category_tree/" + rootCategory.toString();
        if (callback != null) {
            url += "/" + callback;
        } else {
            url += "/NULL";
        }

        return Utils._returnLink(url);
    }

    /**
     * Returns a link to download all documents with a given name in JSON(P)  
     * format from the associated Docxpresso SERVER installation
     * 
     * @param string $name the name of the template we are looking for. This
     * method launches a "LIKE" SQL query.
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @param boolean $published if true only "published" templates will be
     * available through the request.
     * @param string $access it can be "all" (deafult value), "public" for only
     * documents declared like public or a "username" to filter by permissions
     * @return string
     * @access public
     */
    public String templatesByName(String name, Map<String, Object> array /*callback = '', published = 0, access = "all"*/)
    {    
        Object callback = array.get("callback") != null ? array.get("callback") : null;
        Object published = array.get("published") != null ? array.get("published") : null;
        Object access = array.get("access") != null ? array.get("access") : "all";

        String url = (_options.get("docxpressoInstallation")).toString();
        try {
            url += "/RESTservices/predefined/documents_by_name/" + URLEncoder.encode(name.toString() ,"UTF-8");
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }

        if (callback != null) {
            url += "/" + callback;
        } else {
            url += "/NULL";
        }

        if (published != null ) {
            url += "/1";
        } else {
            url += "/0";
        }
        
        if (access != null) {
            try {
                url += "/" + URLEncoder.encode(access.toString() ,"UTF-8"); 
              } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
              }
            
        }

        return Utils._returnLink(url);
    }

    /**
     * Returns a link to download the info (thumbnail included) of the most
     * recently edited templates
     * 
     * @param string $limit the number of templates we want to retrieve.
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @param boolean $published if true only "published" templates will be
     * available through the request.
     * @return string
     * @access public
     */
    public String latestTemplates(Integer limit,Map<String, Object> array)
    {   
        Object callback = array.get("callback") != null ? array.get("callback") : null;
        Object published = array.get("published") != null ? array.get("published") : null;
        
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/latest_templates/" + limit;

        if (callback != null) {
            url += "/" + callback;
        } else {
            url += "/NULL";
        }

        if (published != null) {
            url += "/1";
        } else {
            url += "/0";
        }

        return Utils._returnLink(url);
    }

    /**
     * Returns a link to get all templates ids that have certain Tags
     * 
     * @param array $data with the following keys and values
     *      'tags' => (array) list of tags to be searche for
     *      'category' => (integer) the category we want to filter by if any.
     *       Subcategories are not included.
     *      'published' => (boolean) if true only published templates are
     *       returned.
     *      'active' => (boolean)if true (default value) only "non-deleted"
     *       tempaltes are returned.
     *      'sort' => (string) the field used to sort the results.
     *      'order' => (string) possible values are DESC (default) or ASC.
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * available through the request.
     * @return string
     * @access public
     */
    public String searchTemplatesByTag(Map<String, Object> data, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/search_templates_by_tags";

        if (callback != null) {
            url += "/" + callback;
        } else {
            url += "/NULL";
        }

        //we build and options object with the search filters
        Map<Object, Object> options = new HashMap<>();
        if (data.get("tags")!=null) {
            options.put("tags", data.get("tags"));
        }
        if (data.get("category")!=null) {
            options.put("category", data.get("category"));
        }
        if (data.get("published")!=null) {
            options.put("published", data.get("published"));
        }
        if (data.get("active")!=null) {
            options.put("active", data.get("active"));
        }
        if (data.get("sort")!=null) {
            options.put("sort", data.get("sort"));
        }
        if (data.get("order")!=null) {
            options.put("order", data.get("order"));
        }
        
        
        String opt = Utils.base64_encode_url_safe(options.toString());

        return Utils._returnLink(url, null, opt);
    }

     /**
     * Returns a link to list all documents with a given name and/or a   
     * given category from the associated Docxpresso SERVER installation
     * 
     * @param integer $page the page we want to retrieve
     * @param array $data with the following keys and values
     *      'name' => (string) the name we want to filter the results with a 
     *       LIKE%% clause. This parameter is optional.
     *      'category' => (integer) the category we want to filter by if any.
     *       Subcategories are not included.
     *      'numResults' => (int) number of results per page. This number can 
     *       not be bigger than 100 and it defaults to 20.
     *      'sort' => (string) the field used to sort the results.
     *      'order' => (string) possible values are DESC (default) or ASC.
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String listTemplatesPaginated(Integer page, Map<String, Object> data, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;

        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/list_templates_paginated/" + page;

        if (callback != null) {
            url += "/" + callback;
        }
        
        //we build and options object with the search filters
        Map<Object, Object> options = new HashMap<>();
        if (data.get("name")!=null) {
            options.put("identifier", data.get("name"));
        }
        if (data.get("numResults")!=null) {
            options.put("numResults", data.get("numResults"));
        }
        if (data.get("category")!=null) {
            options.put("category", data.get("category"));
        }
        if (data.get("sort")!=null) {
            options.put("sort", data.get("sort"));
        }
        if (data.get("order")!=null) {
            options.put("order", data.get("order"));
        }
        
        String opt = Utils.base64_encode_url_safe(options.toString());

        return Utils._returnLink(url, null, opt);
    }

    /**
     * Returns a link to download the data of a given single usage JSON(P)  
     * format from the associated Docxpresso SERVER installation
     * 
     * @param integer $limit the max numbers of uses to be downloaded. This
     * parameter is compulsory. If bigger then the max allowed limit the number
     * of results will be truncated.
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String lastUsages(Integer limit, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;

        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/last_usages/" + limit;

        if (callback != null) {
            url += "/" + callback;
        }
		
        return Utils._returnLink(url);
    }

    /**
     * Returns a list of the lestest used templates
     * 
     * @param integer $limit the max numbers of uses to be downloaded. This
     * parameter is compulsory. If bigger then the max allowed limit the number
     * of results will be truncated. Default number is 10.
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String lastUsedTemplates(Map<String, Object> array)
    {   
        Object limit = array.get("limit") != null ? array.get("limit") : 10;
        Object callback = array.get("callback") != null ? array.get("callback") : null;

        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/last_used_templates/" + limit;

        if (callback != null) {
            url += "/" + callback;
        }
		
        return Utils._returnLink(url);
    }

    /**
     * Returns a link to download all template usage data in JSON(P)  
     * format  for a given template id from the associated Docxpresso 
     * SERVER installation
     * 
     * @param array $data with the following keys and values
     *      'id' => (int) the id of the template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'identifier' => (string) the identifier field of an usage. The
     *       default value is an empty string
     *      'reference' => (string) the reference field of an usage. The
     *       default value is an empty string
     *      'enduserid' => (string) the end user id of a particular usage.
     *       Default value is an empty string.
     *      'period' => (string) if given will overwrite the given startDate and
     *       enddate parameters. The possible values are: today, 
     *       1week (last week), 1month (last month), 3month (last quarter),
     *       year (last year). The default value is empty
     *      'startDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened after it. Default value is an empty 
     *       string. Optionally you may include de time in the format hh:mm:ss
     *      'endDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened before it. Default value is an empty 
     *       string. Optionally you may include de time in the format hh:mm:ss
     *      'locked' => (integer) it can be zero for all usages (default), 1 if
     *       we only want usages that have been set as completed or 2 for the 
     *       opposite.
     *      'firstResult' => (int) query offset. Default value is 0; 
     *      'maxResults' => (int) maximum number of results. Beware that
     *       each installation may have upper limits to this number.
     *       Default value is empty and Docxpresso default will be used (50).
     *      'sort' => (string) the field used to sort the results.
     *      'order' => (string) possible values are DESC (default) or ASC.
     *      'client' => (integer) cliend id. Only for multi tenant instances. 
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String dataByTemplate(Map<String, Object>  data, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;

        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/data_by_template/" + data.get("id");
		
        if (callback != null) {
            url += "/" + callback;
        }

        //we build and options object with the search filters
        Map<Object, Object> options = new HashMap<>();
        if (data.get("identifier")!=null) {
            options.put("identifier", data.get("identifier"));
        }
        if (data.get("client")!=null) {
            options.put("client", data.get("client"));
        }
        if (data.get("reference")!=null) {
            options.put("reference", data.get("reference"));
        }
        if (data.get("enduserid")!=null) {
            options.put("enduserid", data.get("enduserid"));
        }
        if (data.get("locked")!=null) {
            options.put("locked", data.get("locked"));
        }
        //dates must be in the format 2016-01-30
        if (data.get("startDate")!=null) {
            options.put("startDate", data.get("startDate"));
        }
        if (data.get("endDate")!=null) {
            options.put("endDate", data.get("endDate"));
        }
        if (data.get("period")!=null) {
            options.put("period", data.get("period"));
        }
        if (data.get("firstResult")!=null) {
            options.put("firstResult", data.get("firstResult"));
        }
        if (data.get("maxResults")!=null) {
            options.put("maxResults", data.get("maxResults"));
        }
        if (data.get("sort")!=null) {
            options.put("sort", data.get("sort"));
        }
        if (data.get("order")!=null) {
            options.put("order", data.get("order"));
        }
        String opt = Utils.base64_encode_url_safe(options.toString());

        return Utils._returnLink(url, data.get("id").toString(), opt);
    } 

    /**
     * Returns a link to download the 'paginated' template usage data in JSON(P)  
     * format  for a given template id from the associated Docxpresso 
     * SERVER installation
     * 
     * @param integer $page the page we want to retrieve
     * @param array $data with the following keys and values
     *      'id' => (int) the id of the template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'identifier' => (string) the identifier field of an usage. The
     *       default value is an empty string
     *      'reference' => (string) the reference field of an usage. The
     *       default value is an empty string
     *      'enduserid' => (string) the end user id of a particular usage.
     *       Default value is an empty string.
     *      'period' => (string) if given will overwrite the given startDate and
     *       enddate parameters. The possible values are: today, 
     *       1week (last week), 1month (last month), 3month (last quarter),
     *       year (last year). The default value is empty
     *      'startDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened after it. Default value is an empty 
     *       string.
     *      'endDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened before it. Default value is an empty 
     *       string.
     *      'locked' => (integer) it can be zero for all usages (default), 1 if
     *       we only want usages that have been set as completed or 2 for the 
     *       opposite.
     *      'numResults' => (int) number of results per page. This number can 
     *       not be bigger than 100 and it defaults to 20.
     *      'sort' => (string) the field used to sort the results.
     *      'order' => (string) possible values are DESC (default) or ASC.
     *      'client' => (integer) cliend id. Only for multi tenant instances. 
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String dataByTemplatePaginated(Integer page, Map<String, Object> data, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/data_by_template_paginated/" + data.get("id") + "/" + page;
		
        if (callback != null) {
                url += "/" + callback;
        }

        //we build and options object with the search filters
        Map<Object, Object> options = new HashMap<>();
        if (data.get("identifier")!=null) {
            options.put("identifier", data.get("identifier"));
        }
        if (data.get("client")!=null) {
            options.put("client", data.get("client"));
        }
        if (data.get("reference")!=null) {
            options.put("reference", data.get("reference"));
        }
        if (data.get("enduserid")!=null) {
            options.put("enduserid", data.get("enduserid"));
        }
        if (data.get("locked")!=null) {
            options.put("locked", data.get("locked"));
        }
        //dates must be in the format 2016-01-30
        if (data.get("startDate")!=null) {
            options.put("startDate", data.get("startDate"));
        }
        if (data.get("endDate")!=null) {
            options.put("endDate", data.get("endDate"));
        }
        if (data.get("period")!=null) {
            options.put("period", data.get("period"));
        }
        if (data.get("numResults")!=null) {
            options.put("numResults", data.get("numResults"));
        }
        if (data.get("sort")!=null) {
            options.put("sort", data.get("sort"));
        }
        if (data.get("order")!=null) {
            options.put("order", data.get("order"));
        }

        String opt = Utils.base64_encode_url_safe(options.toString());

        return Utils._returnLink(url, data.get("id").toString(), opt);
    }

    /**
     * Returns a link to download the 'paginated' template usage data in JSON(P)  
     * format from the associated Docxpresso SERVER installation
     * 
     * @param integer $page the page we want to retrieve
     * @param array $data with the following keys and values
     *      'id' => (int) the id of the template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'identifier' => (string) the identifier field of an usage. The
     *       default value is an empty string
     *      'reference' => (string) the reference field of an usage. The
     *       default value is an empty string
     *       'domain' => (string) the domain field of an usage. The
     *       default value is an empty string
     *      'enduserid' => (string) the end user id of a particular usage.
     *       Default value is an empty string.
     *      'period' => (string) if given will overwrite the given startDate and
     *       enddate parameters. The possible values are: today, 
     *       1week (last week), 1month (last month), 3month (last quarter),
     *       year (last year). The default value is empty
     *      'startDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened after it. Default value is an empty 
     *       string.
     *      'endDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened before it. Default value is an empty 
     *       string.
     *      'locked' => (integer) it can be zero for all usages (default), 1 if
     *       we only want usages that have been set as completed or 2 for the 
     *       opposite.
     *      'numResults' => (int) number of results per page. This number can 
     *       not be bigger than 100 and it defaults to 20.
     *      'sort' => (string) the field used to sort the results.
     *      'order' => (string) possible values are DESC (default) or ASC.
     *      'client' => (integer) cliend id. Only for multi tenant instances. 
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String getUsageDataPaginated(Integer page, Map<String, Object> data, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/get_usage_data_paginated/" + page;
		
        if (callback != null) {
                url += "/" + callback;
        }

        //we build and options object with the search filters
        Map<Object, Object> options = new HashMap<>();
        if (data.get("id")!=null) {
            options.put("id", data.get("id"));
        }
        if (data.get("identifier")!=null) {
            options.put("identifier", data.get("identifier"));
        }
        if (data.get("client")!=null) {
            options.put("client", data.get("client"));
        }
        if (data.get("reference")!=null) {
            options.put("reference", data.get("reference"));
        }
        if (data.get("enduserid")!=null) {
            options.put("enduserid", data.get("enduserid"));
        }
        if (data.get("domain")!=null) {
            options.put("domain", data.get("domain"));
        }
        if (data.get("locked")!=null) {
            options.put("locked", data.get("locked"));
        }
        //dates must be in the format 2016-01-30
        if (data.get("startDate")!=null) {
            options.put("startDate", data.get("startDate"));
        }
        if (data.get("endDate")!=null) {
            options.put("endDate", data.get("endDate"));
        }
        if (data.get("period")!=null) {
            options.put("period", data.get("period"));
        }
        if (data.get("numResults")!=null) {
            options.put("numResults", data.get("numResults"));
        }
        if (data.get("sort")!=null) {
            options.put("sort", data.get("sort"));
        }
        if (data.get("order")!=null) {
            options.put("order", data.get("order"));
        }

        String opt = Utils.base64_encode_url_safe(options.toString());

        return Utils._returnLink(url, null, opt);
    }

     /**
     * Returns a link to download the data of a given single usage JSON(P)  
     * format from the associated Docxpresso SERVER installation
     * 
     * @param integer $usageId the id of a particular usage
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String dataByUsage(Integer usageId, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/data_by_usage/" + usageId;
		
        if (callback != null) {
            url += "/" + callback;
        }
		
        return Utils._returnLink(url, usageId.toString());
    }

    /**
     * Returns a link to generate a HTML or CSV file for all the data usage
     * for a given template  
     * 
     * @param array $data with the following keys and values
     *      'id' => (int) the id of the template. This value is compulsory and 
     *       must correspond to a valid template id.
     *      'format' => (string) it may be html or csv (default)
     *      'identifier' => (string) the identifier field of an usage. The
     *       default value is an empty string
     *      'reference' => (string) the reference field of an usage. The
     *       default value is an empty string
     *      'enduserid' => (string) the end user id of a particular usage.
     *       Default value is an empty string.
     *      'idrange' => (string) the range of ids that should be filtered, i.e.
     *       120-145. Default value is an empty string.
     *      'period' => (string) if given will overwrite the given startDate and
     *       enddate parameters. The possible values are: today, 
     *       1week (last week), 1month (last month), 3month (last quarter),
     *       year (last year). The default value is empty
     *      'startDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened after it. Default value is an empty 
     *       string.
     *      'endDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened before it. Default value is an empty 
     *       string.
     *      'locked' => (integer) it can be zero for all usages (default), 1 if
     *       we only want usages that have been set as completed or 2 for the 
     *       opposite.
     *      'firstResult' => (int) query offset. Default value is 0; 
     *      'maxResults' => (int) maximum number of results. Beware that
     *       each installation may have upper limits to this number.
     *       Default value is an empty and in that case Docxpresso
     *      'sort' => (string) the field used to sort the results.
     *      'order' => (string) possible values are DESC (default) or ASC.
     *      'client' => (integer) cliend id. Only for multi tenant instances. 
     * @return string
     * @access public
     */
    public String dataDigestByUsage(Map<String, Object> data)
    {    
        String preUrl = (_options.get("docxpressoInstallation")).toString() + "/data/digest/" + data.get("id");
        String url = Utils._returnLink(preUrl, data.get("id").toString(), null) + "&";

        //we build the URL with the search filters and output format
        if (data.get("format")!=null) {
            url += "format=" + data.get("format") + "&";
        } else {
            url += "format=csv&";
        }
        if (data.get("identifier")!=null) {
            url += "identifier=" + data.get("identifier") + "&";
        } 
        if (data.get("client")!=null) {
            url += "client=" + data.get("client") + "&";
        }
        if (data.get("reference")!=null) {
            url += "reference=" + data.get("reference") + "&";
        }
        if (data.get("enduserid")!=null) {
            url += "enduserid=" + data.get("enduserid") + "&";
        }
        //dates like before and after must be in the format 2016-01-30
        if (data.get("startDate")!=null) {
            url += "before=" + data.get("startDate") + "&";
        }
        if (data.get("endDate")!=null) {
            url += "after=" + data.get("endDate") + "&";
        }
        if (data.get("period")!=null) {
            url += "period=" + data.get("period") + "&";
        }
        if (data.get("domain")!=null) {
            url += "domain=" + data.get("domain") + "&";
        }
        if (data.get("idrange")!=null) {
            url += "idrange=" + data.get("idrange") + "&";
        }
        if (data.get("locked")!=null) {
            url += "locked=" + data.get("locked") + "&";
        }
        if (data.get("maxResults")!=null) {
            url += "maxResults=" + data.get("maxResults") + "&";
        }
        if (data.get("sort")!=null) {
            url += "sort=" + data.get("sort") + "&";
        }
        if (data.get("order")!=null) {
            url += "order=" + data.get("order") + "&";
        }
        
        url += "extra=1";
        return Utils._returnLink(url, data.get("id").toString());
    }

    /**
     * Returns a link to download all template forwarded docs in JSON(P)  
     * format  for a given template id from the associated Docxpresso 
     * SERVER installation
     * 
     * @param array $data with the following keys and values
     *      'id' => (int) the id of the template.
     *       This value is compulsory and must correspond to a valid template
     *       id.
     *      'identifier' => (string) the identifier field of an usage. The
     *       default value is an empty string
     *      'reference' => (string) the reference field of an usage. The
     *       default value is an empty string
     *      'processed' => (integer) a flag that allows to set the processing
     *       status. By default forwarded documents have a processed status of
     *       0. This processed status may be managed remotely via the
     *       fetchForwardedDocument method of the SDK
     *      'period' => (string) if given will overwrite the given startDate and
     *       enddate parameters. The possible values are: today, 
     *       1week (last week), 1month (last month), 3month (last quarter),
     *       year (last year). The default value is empty
     *      'startDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened after it. Default value is an empty 
     *       string.
     *      'endDate' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened before it. Default value is an empty 
     *       string.
     *      'firstResult' => (int) query offset. Default value is 0; 
     *      'maxResults' => (int) maximum number of results. Beware that
     *       each installation may have upper limits to this number.
     *       Default value is empty and Docxpresso default will be used (50).
     *      'sort' => (string) the field used to sort the results.
     *      'order' => (string) possible values are DESC (default) or ASC.
     *      'client' => (integer) cliend id. Only for multi tenant instances. 
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String forwardedDocumentsByTemplate(Map<String, Object> data, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/forwarded_documents_by_template/" + data.get("id");
		
        if (callback != null) { 
                url += "/" + callback;
        }

        //we build and options object with the search filters
        Map<Object, Object> options = new HashMap<>();
        if (data.get("identifier")!=null) {
            options.put("identifier", data.get("identifier"));
        }
        if (data.get("client")!=null) {
            options.put("client", data.get("client"));
        }
        if (data.get("reference")!=null) {
            options.put("reference", data.get("reference"));
        }
        if (data.get("processed")!=null) {
            options.put("processed", data.get("processed"));
        }
        //dates must be in the format 2016-01-30
        if (data.get("startDate")!=null) {
            options.put("startDate", data.get("startDate"));
        }
        if (data.get("endDate")!=null) {
            options.put("endDate", data.get("endDate"));
        }
        if (data.get("period")!=null) {
            options.put("period", data.get("period"));
        }
        if (data.get("firstResult")!=null) {
            options.put("firstResult", data.get("firstResult"));
        }
        if (data.get("maxResults")!=null) {
            options.put("maxResults", data.get("maxResults"));
        }
        if (data.get("sort")!=null) {
            options.put("sort", data.get("sort"));
        }
        if (data.get("order")!=null) {
            options.put("order", data.get("order"));
        }

        String opt = Utils.base64_encode_url_safe(options.toString());

        return Utils._returnLink(url, data.get("id").toString(), opt);
    }

    /**
     * Returns a link to modify the data and options of a particular usage.
     * In order to avoid unwanted/accidental changes of data we request together
     * with the usageId its unique assocated token
     * 
     * 
     * @param integer $usageId the id of a particular usage
     * @param array $data with the following keys and values
     *      'token' => (string) the unique identifier of this use. This is
     *       only required for security to avoid unintentional changes in the 
     *       usage data.
     *      'identifier' => (string) the identifier value that we want to
     *       overwrite. Default value is an empty string 
     *      'reference' => (string) the reference value that we want to
     *       overwrite. Default value is an empty string 
     *      'validated' => (boolean) an optional boolean parameter to overwrite
     *       the previous value.
     *      'tampered' => (boolean) an optional boolean parameter to overwrite
     *       the previous value.
     *      'locked' => (boolean) an optional boolean parameter to overwrite
     *       the previous value. It is also may be interpreted as "completed"
     *      'domain' => (string) an optional parameter to overwrite the domain
     *       property.
     *      'user' => (string) an optional parameter to overwrite the user
     *       name associated with the selected usage.
     *       the previous value. It is also may be interpreted as "completed"
     *      'comments' => (string) if not empty it will overwrite the comments
     *       associated with this usage. Default value is an empty 
     *       string.
     *      'percentageCompleted' => (integer) if given  overwrites the value of
     *       the percentage completed. 
     *       string.
     *      'varData' => JSON data that will be merged with the previous stored
     *       document data (by the time being only variable values).
     *      'callback' => function name for JSONP calls.
     *      'plugin' => (boolean) if true the petition may directly come from
     *       a plugin user so further security checks may be carried out to test
     *       for data coherence and permissions.
     * @return string
     * @access public
     */
    public String modifyUsageData(Integer usageId,Map<String, Object> data)
    {    
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/modify_usage_data/" + usageId;
        
        Map<Object, Object> options = new HashMap<>();
        if (data.get("token")!=null) {
            options.put("token", data.get("token"));
        }
        if (data.get("identifier")!=null) {
            options.put("identifier", data.get("identifier"));
        }
        if (data.get("reference")!=null) {
            options.put("reference", data.get("reference"));
        }
        if (data.get("domain")!=null) {
            options.put("domain", data.get("domain"));
        }
        if (data.get("user")!=null) {
            options.put("enduserid", data.get("user"));
        }
        if (data.get("tampered")!=null) {
            options.put("tampered", 1);
        } else {
            options.put("tampered", 0);
        }
        if (data.get("validated")!=null) {
            options.put("validated", 1);
        } else {
            options.put("validated", 0);
        }
        if (data.get("locked")!=null) {
            options.put("locked", 1);
        } else {
            options.put("locked", 0);
        }
        if (data.get("comments")!=null) {
            options.put("comments", data.get("comments"));
        }
        if (data.get("varData")!=null) {
            options.put("data", data.get("varData"));
        }
        if (data.get("callback")!=null) {
            options.put("callback", data.get("callback"));
        }
        if (data.get("plugin")!=null) {
            options.put("plugin", data.get("plugin"));
        }
        
        String opt = Utils.base64_encode_url_safe(options.toString());
        return Utils._returnLink(url, usageId.toString(), opt);
    }

    /**
     * Returns a link to get a JSON with both the document base64 encoded 
     * together with the other usage data 
     * 
     * @param integer $usageId the id of a particular usage
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String documentAndDataByUsage(Integer usageId, String... varCallback)
    {   
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/document_and_data_by_usage/" + usageId; 

        if (callback != null) {
            url += "/" + callback;
        }
		
        return Utils._returnLink(url, usageId.toString());
    }

    /**
     * Returns a link to download basic statistical data like number of uses
     * and last usage
     * 
     * @param mixed $id template id. If set to 'all' the data
     * for all available templates will be provided
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty or NULL plain JSON will be returned.
     * @param boolean $published if true only "published" templates will be
     * available through the request.
     * @return string
     * @access public
     */
    public String dataStatistics(Map<String, Object> preVariables)
    {    
        String id = preVariables.get("id") != null ? preVariables.get("id").toString() : "all";
        String callback = preVariables.get("callback") != null ? preVariables.get("callback").toString() : "";
        String published = preVariables.get("published") != null ? preVariables.get("published").toString() : "0";

        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/data_statistics"; 
		
        if (!id.equals("all")) {
            url += "/" + id;
        } else {
            url += "/all";
        }
		
        if (!callback.equals("")) {
            url += "/" + callback;
        } else if (!published.equals("0")) {
            url += "/NULL";
        }

        if (!published.equals("0")) {
            url += "/1";
        }

        return Utils._returnLink(url, id);
    }

    /**
     * Returns a link to download the total usage count group by day 
     * 
     * @param array $data with the following keys and values
     *      'id' => (mixed) the id of the template. If set to 'all' the data
     *       for all available templates will be provided.
     *      'after' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened after it. Default value is an empty 
     *       string.
     *      'before' => (string) a date in the format yyyy-mm-dd that will
     *       select usages that happened before it. Default value is an empty 
     *       string.
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty or NULL plain JSON will be returned.
     * @return string
     * @access public
     */
    public String usageCount(Map<String, Object> data,String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String id = data.get("id") != null ? data.get("id").toString() : "all";

        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/usage_count/" + id; 
		
        if (callback != null) {
            url += "/" + callback;
        }

        //we build and options object with the search filters
        Map<Object, Object> options = new HashMap<>();
        if (data.get("before")!=null) {
            options.put("before", data.get("before"));
        }
        if (data.get("after")!=null) {
            options.put("after", data.get("after"));
        }

        String opt = Utils.base64_encode_url_safe(options.toString());
        return Utils._returnLink(url, id.toString(), opt);
    }

    /**
     * Returns a link to list of users in JSON(P) format from the associated
     * Docxpresso SERVER installation
     * 
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String userList(String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/users"; 

        if (callback != null) {
            url += "/" + callback;
        }
       
        return Utils._returnLink(url);
    }

     /**
     * Returns a link to check if the the user is logged into the associated
     * Docxpresso SERVER installation
     * 
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String checkUser(String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;

        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/check_user"; 

        if (callback != null) {
            url += "/" + callback;
        }
       
        return Utils._returnLink(url);
    }

    /**
     * Returns a link to clone a template
     * 
     @param integer $id the id of the template we want to clone
     * @param array $data with the following keys and values
     *      'name' => (string) the name given to the cloned template.
     * @return string
     * @access public
     */
    public String cloneTemplate(Integer id, Map<String, Object> data, String... varCallback)
    {    
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        
        String url = (_options.get("docxpressoInstallation")).toString() + "/RESTservices/predefined/clone_template/" + id; 

        if (callback != null) {
            url += "/" + callback;
        }
        
        //we build and options object
        Map<Object, Object> options = new HashMap<>();
        if (data.get("name")!=null) {
            options.put("name", data.get("name"));
        }

        String opt = Utils.base64_encode_url_safe(options.toString());
        return Utils._returnLink(url, id.toString(), opt);
     }


     /**
     * Returns a link to use in a form or via a cURL POST
     * The post fields should include:
     *  'email' the valid email of a registered Docxpresso EDITOR/ADMIN user
     *  'path' a file input field
     *  'name' a unique name for the new template
     *  'config' an array JSON encoded with additional configuration options
     *  'category' the category id where the template should be stored
     *  'tags' a comma separated string of tags associated with the template (optional)
     *  'public' a boolean value (1 or 0) setting internal write/read permissions for
     *   editors. Default value is 1 (all editors have access).
     *  'accessControl' a boolean value (1 or 0). If equals 0 there is free
     *   access to that document by end users. Default value is 1.
     *  'description' an optional description
     * 
     @param integer $id the id of the template we want to update
     * @param array $data with the following keys and values
     *      'responseURL' => redirect URL if used as an action in a form.
     *      'callback' => (string) the callback function if called as JSONP.
     * @return string
     * @access public
     */
    public String createTemplate(Map<String,Object> data)
    {    
        Double num = Math.floor(Math.random() * (9999999 - 9999) + 9999999);
        String id = String.format("%.0f", num);

        String url = (_options.get("docxpressoInstallation")).toString() + "/documents/remote_create_template/" + id; 
        System.out.println(id);
        //we build and options object
        Map<Object, Object> options = new HashMap<>();
        if (data.get("responseURL")!=null) {
            options.put("responseURL", data.get("responseURL"));
        }
        if (data.get("callback")!=null) {
            options.put("callback", data.get("callback"));
        }
       
        String opt = Utils.base64_encode_url_safe(options.toString());
        return Utils._returnLink(url, id, opt);
    }

    /**
     * Returns a link to use in a form or via a cURL POST
     * The post fields should include:
     *  'path' a file input field
     *  'keepThumb' a field that if not empty will preserve the current
     *   document thumbnail
     * 
     @param integer $id the id of the template we want to update
     * @param array $data with the following keys and values
     *      'responseURL' => redirect URL if used as an action in a form.
     *      'callback' => (string) the callback function if called as JSONP.
     * @return string
     * @access public
     */
    public String uploadTemplate(Integer id, Map<String, Object> data)
    {    
        String url = (_options.get("docxpressoInstallation")).toString() + "/documents/upload_template/" + id; 
        
        //we build and options object
        Map<Object, Object> options = new HashMap<>();
        if (data.get("responseURL")!=null) {
            options.put("responseURL", data.get("responseURL"));
        }
        if (data.get("callback")!=null) {
            options.put("callback", data.get("callback"));
        }
        
       
        String opt = Utils.base64_encode_url_safe(options.toString());
        return Utils._returnLink(url, id.toString(), opt);
    }

    /**
     * Returns a link to resend pending webhooks.
     * 
     * @param string $callback the callback name that we want to use for padded
     * JSON responses. If empty plain JSON will be returned.
     * @return string
     * @access public
     */
    public String managePendingWebhooks(String... varCallback)
    {   
        String callback = varCallback.length > 0 ? varCallback[0] : null;
        String url = (_options.get("docxpressoInstallation")).toString() + "/data/webhook/pending"; 

        if (callback != null) {
            url += "/" + callback;
        }

        return Utils._returnLink(url);
    }


    /**
     * Creates the link requested by all other methods
     * 
     * @param string $url
     * @param mixed $id        * @param mixed $opt
     * @return string
     * @access private
     */
    public static String _returnLink(String url, String... array)
    {
        String id = array.length > 0 ? array[0] : null;
        String opt = array.length > 1  ? array[1] : null;
          
        //Number uniqid = 1234;
        //Number timestamp = 56789;
        Long uniqid = Utils.generate_uniqid();
        System.out.println(uniqid);
        Long timestamp = Math.round(Math.floor(new Date().getTime()/1000));
         

        //Integer uniqid = 1234;
        //var timestamp = 56789;

        String control = "";
        if (id!=null){
            control +=  id + "-";
        } 
        control += timestamp + "-" + uniqid;
 
        if (opt!=null){
            control += "-" + opt;
        } 
        String APIKEY = Utils.hmacSha1(control);
        

        var addr = url + '?';
        addr += "timestamp=" + timestamp +"&";
        addr += "uniqid=" + uniqid + "&";
        addr += "APIKEY=" + APIKEY;

        if((opt)!=null){
            addr+= "&options=" + opt;
        }
         
        return addr;
    }

    /*********************************************/
    /*               ACCESS CONTROL              */
    /*      For data exchange with Docxpresso    */
    /*********************************************/ 
    
    /**
     * Whenever interacting with Docxpresso via a requestDataURI or 
     * responseDataURI parameters of the documentPreview method there
     * is an APIKEY based on HMAC that allows to identify petitions as
     * legitimate.
     * This method simplifies the task to check if the request is legitimate
     * by returning a boolean value: true for authorized requests and false
     * otherwise
     * 
     * @param string $data the POST/GET variable data sent along with 
     * the request
     * @param string $timestamp the POST/GET variable timestamp sent along with 
     * the request
     * @param string $uniqid the POST/GET variable uniqid sent along with 
     * the request
     * @param string $APIKEY the POST/GET variable APIKEY sent along with 
     * the request
     * @return boolean
     * @access public
     */
    public Boolean checkAPIKEY(String data, String timestamp, String uniqid, String APIKEY)
    {

        String refAPIKEY = Utils.hmacSha1(timestamp + "-" + uniqid + "-" + data );
        if (refAPIKEY == APIKEY){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Whenever sending a request to an external API service the timestamp
     * and a service token are added to the query parameters.
     * 
     * This method checks that the pair timestamp and token where generated
     * from the given Docxpresso installation
     * 
     * @param string $timestamp the GET variable timestamp sent along with 
     * the request
     * @param string $token the GET variable uniqid sent along with 
     * the request
     * @param integer $expires number of seconds before the token expires. 
     * Default value is 3600.
     * @return boolean
     * @access public
     */
    public Boolean checkServiceToken(String timestamp, String token, Integer... varExpires)
    {
        Integer expires = varExpires.length > 0 ? varExpires[0] : 3600;
        Long currentTime = Math.round(Math.floor( new Date().getTime()/1000));

        Integer myTime = Integer.parseInt(timestamp);
        
        if (currentTime - myTime > expires){
            return false;
        }
        String masterKey = (_options.get("pKey")).toString();
        
        String refToken = Utils.hmacSha1(timestamp + masterKey + timestamp);
        System.out.println(refToken);
        if (refToken == token){
            return true;
        } else {
            return false;
        }
    }
      
    public static String  hmacSha1(String options) {
        String key = (_options.get("pKey")).toString();

        byte [] txtDigest = new DigestUtils(SHA_1).digest(options);
        HmacUtils hm1 = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key);
        String optionsSha1 = hm1.hmacHex(txtDigest); 
        return optionsSha1;
    }

    public static String hmacMd5(String options) {
        String key = (_options.get("pKey")).toString();

        byte [] txtDigest = new DigestUtils(MD5).digest(options);
        HmacUtils hm1 = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key);
        String optionsSha1 = hm1.hmacHex(txtDigest); 
        return optionsSha1;
    }


    /**
     * Returns a 12 chars random OTP
     * 
     * @return string
     * @access private
     */
    public static String _generateOTP ()
    {
        double random = Math.floor(Math.random() * (9999999 - 9999) + 9999999);
        Long timestamp = Math.round(Math.floor( new Date().getTime()/1000));
        var raw = Utils.hmacSha1("otp" + random + "_" + timestamp + "A random sentence");
        return raw.substring(6, 12);
    }

      public static Long generate_uniqid(){
        Long date = new Date().getTime();
        double mathfloor = Math.floor(Math.random() * (9999999 - 99999) + 99999);
        Double uniqId = date + mathfloor;;
        Long unid =  Math.round(uniqId);    
        return unid;
    }

}


    


