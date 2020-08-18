package com.supermap.digicity.sdm.tools.dialogs;


/**
 * 无用，测试用
 */
public class updatetool {
    //可选参数没有赋初值
    private static String remoteaddr="";
    private static String remoteport="";
    private static String name;
    private static String pwd;
    private static String localfilename="";
    private static String remotefilename="";

    public static void main(String[] args) throws Exception {



    };
    private static void setProperty(String[] args) throws Exception {
        if(args.length==0) {
            throw new Exception("请输入参数");
        }

        System.out.println("获取参数名称和参数值");
        for(int i=0;i<args.length;i++) {
            if(args[i].equals("-addr")) {
                remoteaddr=args[i+1];
                continue;
            }
            if(args[i].equals("-port")) {
                remoteport=args[i+1];
                continue;
            }
            if(args[i].equals("-name")) {
                name=args[i+1];
                continue;
            }
            if(args[i].equals("-pwd")) {
                pwd=args[i+1];
                continue;
            }
            if(args[i].equals("-localfilename")) {
                localfilename=args[i+1];
                continue;
            }
            if(args[i].equals("-remotefilename")) {
                remotefilename=args[i+1];
                continue;
            }
        }

    }
    private static void checkProperty(String[] args) throws Exception {
        //ip地址验证规范
        if(!(remoteaddr.matches("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))"))) {

            throw new Exception("ip格式错误");
        }
        //端口验证规范
        if(!(remoteport.matches("([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])"))) {
            throw new Exception("端口号格式错误");
        }
        //用户名验证规范
        if(name!=null){
            if(!(name.matches("^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$"))) {
                throw new Exception("用户名有非法字符");
            }}
        //密码验证规范
        if(pwd!=null){
            if(pwd.matches("^[\\u4e00-\\u9fa5]+$")) {
                throw new Exception("密码有非法字符");
            }  }
        //文件地址验证规范
        if((localfilename.matches(""))) {

        }  //远程文件名验证规范
        if(remotefilename.matches("")) {

        }
        System.out.println(remoteaddr);
        System.out.println(remoteport);
        System.out.println(name);
        System.out.println(pwd);
        System.out.println(localfilename);
        System.out.println(remotefilename);
    }
}
