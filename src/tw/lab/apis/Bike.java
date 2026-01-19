package tw.lab.apis;

import java.io.Serializable;

//沒有寫extends時，預設為extends Object
public class Bike extends Object implements Serializable{
	//屬性
	protected double speed;
	//沒有public只有相同package抓的到
	//private:只有相同calss可以使用
	//protected:只有extands時可以使用
	public Bike() {
		System.out.println("Bike()");
	}
	public Bike(int a) {
		System.out.println("Bike(int)");
	}
	
	//對屬性做動作
	public Bike upSpeed() {
		speed = speed < 1 ? 1 :speed * 1.3;
		return this;
	}	
	public Bike downSpeed() {
		speed = speed < 1 ? 0 :speed * 0.5;
		return this;
	}
	public double getSpeed() {
		return speed;
	}

	@Override
	public String toString() {
		return "Speed: " + speed;
	}
}
