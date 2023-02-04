package com.example.famback.type;

import lombok.Getter;

public enum EmailTemplateType {

    INVITE("        <div style=\"margin: 0 auto; width: 500px; padding: 0.5rem; min-height: 200px; border: 1px solid black; display: flex;\n" +
            "        box-shadow: 5px 5px 10px rgba(0,0,0,0.1);\">\n" +
            "        <div style=\"\">\n" +
            "            <img src=\"https://image-server.dotorim.com/dotoori_logo.png\" style=\"width: 150px; height: auto;\" alt=\"\">\n" +
            "        </div>\n" +
            "        <section>\n" +
            "            <h3>안녕하세요.<br/></h3>\n" +
            "            \n" +
            "            <h4>fam 프로젝트 에서 인증코드를 보냅니다.</h4>\n" +
            "            <p><a href='{0}'>초대링크</a></p>\n" +
            "            <h5 style=\"display: flex; flex-direction: column; justify-content: end;\">\n" +
            "                <div style=\"display: flex; align-items: end;\">\n" +
            "                    <img src=\"https://image-server.dotorim.com/fam_logo_green.png\" style=\"width: 50px; height: auto;\" alt=\"\">\n" +
            "                    <a href=\"https://fam.dotoori.com\" class=\"\">fam.dotoori.com</a>\n" +
            "                </div>\n" +
            "                <br/>\n" +
            "                <span>여러 모임에 도움을 주기 위한 사이트입니다.</span>\n" +
            "            </h5>\n" +
            "        </section>\n" +
            "    </div>"),
    EMAIL_AUTH_CODE("    <div style=\"margin: 0 auto; width: 500px; padding: 0.5rem; min-height: 200px; border: 1px solid black; display: flex;\n" +
            "        box-shadow: 5px 5px 10px rgba(0,0,0,0.1);\">\n" +
            "        <div style=\"\">\n" +
            "            <img src=\"https://image-server.dotorim.com/dotoori_logo.png\" style=\"width: 150px; height: auto;\" alt=\"\">\n" +
            "        </div>\n" +
            "        <section>\n" +
            "            <h3>안녕하세요.<br/></h3>\n" +
            "            \n" +
            "            <h4>fam 프로젝트 에서 인증코드를 보냅니다.</h4>\n" +
            "            <p>인증코드 : {0}</p>\n" +
            "            <h5 style=\"display: flex; flex-direction: column; justify-content: end;\">\n" +
            "                <div style=\"display: flex; align-items: end;\">\n" +
            "                    <img src=\"https://image-server.dotorim.com/fam_logo_green.png\" style=\"width: 50px; height: auto;\" alt=\"\">\n" +
            "                    <a href=\"https://fam.dotoori.com\" class=\"\">fam.dotoori.com</a>\n" +
            "                </div>\n" +
            "                <br/>\n" +
            "                <span>여러 모임에 도움을 주기 위한 사이트입니다.</span>\n" +
            "            </h5>\n" +
            "        </section>\n" +
            "    </div>")
    ;
    @Getter
    private final String emailTemplate;

    EmailTemplateType(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }
}
