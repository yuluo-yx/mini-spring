package indi.yuluo.springframework.test.bean;

/**
 * @author yuluo
 * @createTime 2023-02-07  20:24
 * @des null
 */

public class Husband {

	private Wife wife;

	public String queryWife(){
		return "Husband.wife";
	}

	public Wife getWife() {
		return wife;
	}

	public void setWife(Wife wife) {
		this.wife = wife;
	}

}
