package indi.yuluo.springframework.test.bean;

/**
 * @author yuluo
 * @createTime 2023-02-07  20:24
 * @des null
 */

public class Wife {

	private Husband husband;
	private IMother mother; // 婆婆

	public String queryHusband() {
		return "Wife.husband、Mother.callMother：" + mother.callMother();
	}

	public Husband getHusband() {
		return husband;
	}

	public void setHusband(Husband husband) {
		this.husband = husband;
	}

	public IMother getMother() {
		return mother;
	}

	public void setMother(IMother mother) {
		this.mother = mother;
	}

}
