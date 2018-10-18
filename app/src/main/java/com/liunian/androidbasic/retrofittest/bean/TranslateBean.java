package com.liunian.androidbasic.retrofittest.bean;

import java.util.List;

/**
 * Created by dell on 2018/9/6.
 */

public class TranslateBean {

    /**
     * status : 1
     * content : {"from":"en-EU","to":"zh-CN","vendor":"tencent","out":"女人的心是一片深藏着秘密的海洋","ciba_use":"来自机器翻译。","ciba_out":"","err_no":0}
     */

    private int status;
    private ContentBean content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * from : en-EU
         * to : zh-CN
         * vendor : tencent
         * out : 女人的心是一片深藏着秘密的海洋
         * ciba_use : 来自机器翻译。
         * ciba_out :
         * err_no : 0
         */

        private String from;
        private String to;
        private String vendor;
        private String out;
        private String ciba_use;
        private String ciba_out;
        private int err_no;
        private List<String> word_mean;

        public List<String> getWord_mean() {
            return word_mean;
        }

        public void setWord_mean(List<String> word_mean) {
            this.word_mean = word_mean;
        }

        public String getWordMeanString() {
            if (word_mean == null) {
                return null;
            } else {
                String wordMeanString = "";
                for (String wordLine : word_mean) {
                    wordMeanString += wordLine;
                    wordMeanString += "\n";
                }
                return wordMeanString;
            }
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public String getCiba_use() {
            return ciba_use;
        }

        public void setCiba_use(String ciba_use) {
            this.ciba_use = ciba_use;
        }

        public String getCiba_out() {
            return ciba_out;
        }

        public void setCiba_out(String ciba_out) {
            this.ciba_out = ciba_out;
        }

        public int getErr_no() {
            return err_no;
        }

        public void setErr_no(int err_no) {
            this.err_no = err_no;
        }
    }
}
