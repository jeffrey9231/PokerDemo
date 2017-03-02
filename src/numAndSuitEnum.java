
public enum numAndSuitEnum {

	// 利用构造函数传参

    T("T",10), J("J",11), Q("Q",12), K("K",13), A("A",1), C("C",1), D("D",2), H("H",3), S("S",4);

    private String nCode;
	private int num;

    // 构造函数，枚举类型只能为私有

    private numAndSuitEnum(String nCode,int num) {
        this.nCode = nCode;
        this.num = num;
    }
    
    public int getNum(){
    	return num;
    }

}
