package com.ylz.packcommon.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.parser.Tag;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zk
 * Date: 13-1-30
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */
public class JsoupHtml {
    /**
     * 截取字符串长字，保留HTML格式
     * @param content
     * @param len 字符长度
     */
    public static String truncateHTML(String content,int len){
        Document dirtyDocument = Jsoup.parse(content);
        Element source = dirtyDocument.body();
        Document clean = Document.createShell(dirtyDocument.baseUri());
        Element dest = clean.body();
        truncateHTML(source,dest,len);
        return dest.outerHtml();
    }
    public static String truncateHTMLTEXT(String content,int len){
        Document dirtyDocument = Jsoup.parse(content);
        Element source = dirtyDocument.body();
        Document clean = Document.createShell(dirtyDocument.baseUri());
        Element dest = clean.body();
        truncateHTML(source,dest,len);
        org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(dest.outerHtml());
        return doc.body().text();
    }
    private static void truncateHTML(Element source, Element dest,int len) {
        List<Node> sourceChildren = source.childNodes();
        for (Node sourceChild : sourceChildren) {
            if (sourceChild instanceof Element) {
                Element sourceEl = (Element) sourceChild;
                Element  destChild = createSafeElement(sourceEl);
                int txt = dest.text().length();
                if(txt>=len){
                    break;
                }else{
                    len = len - txt;
                }
                dest.appendChild(destChild);
                truncateHTML(sourceEl, destChild,len);
            } else if (sourceChild instanceof TextNode) {
                int destLeng = dest.text().length();
                if(destLeng>=len){
                    break;
                }
                TextNode sourceText = (TextNode) sourceChild;
                int  txtLeng = sourceText.getWholeText().length();
                if((destLeng+txtLeng) > len){
                    int tmp = len - destLeng;
                    String txt = sourceText.getWholeText().substring(0,tmp);
                    TextNode destText = new TextNode(txt, sourceChild.baseUri());
                    dest.appendChild(destText);
                    break;
                }else{
                    TextNode destText = new TextNode(sourceText.getWholeText(), sourceChild.baseUri());
                    dest.appendChild(destText);
                }
            }
        }
    }

    private static  Element createSafeElement(Element sourceEl) {
        String sourceTag = sourceEl.tagName();
        Attributes destAttrs = new Attributes();
        Element dest = new Element(Tag.valueOf(sourceTag), sourceEl.baseUri(), destAttrs);
        Attributes sourceAttrs = sourceEl.attributes();
        for (Attribute sourceAttr : sourceAttrs) {
            destAttrs.put(sourceAttr);
        }
        return dest;
    }
}
