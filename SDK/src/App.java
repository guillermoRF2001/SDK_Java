import com.SDK.model.Utils;
import java.util.HashMap;
import java.util.Map;
public class App {
    public static void main(String[] args) throws Exception {

        Map<String, Object> map1 = new HashMap<>();
        map1.put("pKey", "pKey");
        map1.put("docxpressoInstallation", "docxpressoInstallation");
        Utils util1 = new Utils(map1);
        Map<String, Object> varArray = new HashMap<>();
        Map<String, Object> varArray2 = new HashMap<>();
        
        //varArray.put("token","79f89f0a91b101bc38ff5f0d05c38916");

        System.out.println(Utils.apikey_control("ddssfd","abdc","abcd"));
        System.out.println(Utils.base64_encode_url_safe("abcdefg"));
        System.out.println(Utils.base64_decode_url_safe("YWJjZGVmZw,,"));
        System.out.println(util1.modifyPassword("gmail@gmail.com","password",varArray));
        System.out.println( util1.validateDocument(varArray));
        System.out.println( util1.sendEditLinkByEmail(varArray,varArray2));
        System.out.println( util1.validationsByTemplate(varArray));
        System.out.println( util1.viewDocument(varArray));
        System.out.println( util1.viewHistoryDocument(varArray));
         System.out.println( util1.regenerateDocument(varArray));
        System.out.println( util1.requestDocument(varArray));
        System.out.println( util1.revisionsByTemplate(varArray));
        System.out.println( util1.downloadAttachment(varArray));
        System.out.println( util1.downloadDocument(varArray));
        System.out.println( util1.fetchForwardedDocument(varArray));
        System.out.println( util1.getAnnexData("ad0c670a9d1f9dcb1648a737e96805e5"));
        System.out.println( util1.getTemplateData(5));
        System.out.println( util1.getTemplateThumbnail(5));
        System.out.println( util1.getTemplateSignatureData(5));
        System.out.println( util1.getUsageCurrent("5"));
        System.out.println( util1.getUsageHistory("5"));
        System.out.println( util1.modifyTemplateData(varArray));
        System.out.println( util1.accessByTokenAction(varArray));
        System.out.println( util1.createUser(varArray));
        System.out.println( util1.createWorkflow(varArray,varArray2));
        System.out.println( util1.getWorkflowData(5));
        System.out.println( util1.listCategories());
        System.out.println( util1.documentsByCategory(5,varArray));
        System.out.println( util1.modifySignatureProvider("example","example"));
        System.out.println( util1.documentTree(varArray));
        System.out.println( util1.categoryTree(varArray));
        System.out.println( util1.templatesByName("Guillermo", varArray));
        System.out.println( util1.latestTemplates(30, varArray));
        System.out.println( util1.searchTemplatesByTag(varArray));
        System.out.println( util1.listTemplatesPaginated(2,varArray));
        System.out.println( util1.lastUsages(1));
        System.out.println( util1.lastUsedTemplates(varArray));
        System.out.println( util1.dataByTemplate(varArray));
        System.out.println( util1.dataByTemplatePaginated(1,varArray));
        System.out.println( util1.getUsageDataPaginated(1,varArray));
        System.out.println( util1.dataDigestByUsage(varArray));
        System.out.println( util1.forwardedDocumentsByTemplate(varArray));
        System.out.println( util1.modifyUsageData(7696,varArray));
        System.out.println( util1.documentAndDataByUsage(1));
        System.out.println( util1.dataStatistics(varArray));
        System.out.println( util1.usageCount(varArray));
        System.out.println( util1.userList());
        System.out.println( util1.checkUser());
        System.out.println( util1.cloneTemplate(5,varArray));
        System.out.println( util1.createTemplate(varArray));
        System.out.println( util1.checkAPIKEY("1","2","3","4"));
        System.out.println( util1.checkServiceToken("1","2"));
        System.out.println( util1.uploadTemplate(445,varArray));
        
    }
}
