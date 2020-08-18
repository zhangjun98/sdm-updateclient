package com.supermap.digicity.sdm.enums;

/**
 * 手动更新的注册表映射类，由于注册表不能有中文 所以 中文产品名称必须要有对应的英文的产品名称映射，
 * 暂时保留，后期可能会需要
 */
public enum  ProductType {
   // 数据质检系统
    //时空云数据管理子系统
   iserver{
       @Override
       public int value() {
           return 3;
       }

       @Override
       public String getCNString() {
           return "iserver";
       }
   },
   数据质检系统{
       @Override
       public int value() {
           return 3;
       }

       @Override
       public String getCNString() {
           return "test";
       }
   },
    时空云数据管理子系统{
        @Override
        public int value() {
            return 4;
        }

        @Override
        public String getCNString() {
            return "datamanger";
        }
    };

    public abstract String getCNString();

    public abstract int value();
    @Override
    public String toString(){
        return getCNString();
    }
}
