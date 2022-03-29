package 创建的方式;

public class 多线程的优势 {
    public static void main(String[] args) {
        //执行2次操作,每次循环++10亿次
        int num=10_0000_0000;
        //记录执行时间
        long start=System.currentTimeMillis();
        for(int i=0;i<2;i++){
            for(int j=0;j<num;j++){
                //不需要任何代码,j已经++10亿次了
            }
        }
        long end=System.currentTimeMillis();
        System.out.printf("执行时间: %s",end-start);
    }
}
