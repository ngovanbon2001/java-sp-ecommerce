package ihanoi.ihanoi_backend.util;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class HtmlSanitizerUtils {
    public static String sanitizeHtml(String htmlContent) {
        // Định nghĩa chính sách cho phép các thẻ an toàn
//        PolicyFactory policy = new HtmlPolicyBuilder()
//                .allowElements("p", "b", "i", "a", "ul", "ol", "li", "strong", "em")
//                .allowAttributes("href").onElements("a")
//                .toFactory();
//
//        // Vệ sinh nội dung HTML
//        return policy.sanitize(htmlContent);
        return htmlContent;
    }
}
