package com.mazentop.plugins.session;

import java.util.Objects;

/**
 *  请求计数器
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/2 20:39
 */
public class RequestCount {


    private static int getSessionAttributeCount(CurrentLimiting currentLimiting) {
        int curCount = 0;
        if(!Objects.isNull(SessionUtil.getSessionAttribute(currentLimiting.name))) {
            curCount = (int) SessionUtil.getSessionAttribute(currentLimiting.name);
        }
        return curCount;
    }

    public static boolean isCurrentLimiting(CurrentLimiting currentLimiting) {
        return currentLimiting.limit < getSessionAttributeCount(currentLimiting);
    }

    public static boolean isViewCode(CurrentLimiting currentLimiting) {
        return currentLimiting.limit == getSessionAttributeCount(currentLimiting);
    }

    public static int setSessionAttributeCount(CurrentLimiting currentLimiting) {
        int curCount = getSessionAttributeCount(currentLimiting);
        SessionUtil.setSessionAttribute(currentLimiting.name, curCount + 1);
        return curCount;
    }

    public enum CurrentLimiting {
        /**
         * web表单提交 邮件订阅
         */
        WEBFORM_SUBSCRIPTION("subscription", 5);

        private String name;

        private int limit;

        CurrentLimiting(String name, int limit) {
            this.name = name;
            this.limit = limit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public static CurrentLimiting  getCurrentLimiting(String name) {
            for(CurrentLimiting currentLimiting : CurrentLimiting.values()) {
                if(currentLimiting.name.equals(name)) {
                    return  currentLimiting;
                }
            }
            return CurrentLimiting.WEBFORM_SUBSCRIPTION;
        }

    }
}
