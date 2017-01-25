package threads;

public class PhilosophersEating {
	public static final String SPOONE_1 = "Spoone1";
	public static final String SPOONE_2 = "Spoone2";
	public static final String SPOONE_3 = "Spoone3";
	public static final String SPOONE_4 = "Spoone4";
	public static final String SPOONE_5 = "Spoone5";
	public volatile boolean lock1 = false;
	public volatile boolean lock2 = false;
	public volatile boolean lock3 = false;
	public volatile boolean lock4 = false;
	public volatile boolean lock5 = false;
	private int numberFreeLock;
	
	public static void main(String[] args) {
		PhilosophersEating eating = new PhilosophersEating();
		Philosopher p1 = new Philosopher(eating, 1);
		Philosopher p2 = new Philosopher(eating, 2);
		Philosopher p3 = new Philosopher(eating, 3);
		Philosopher p4 = new Philosopher(eating, 4);
		Philosopher p5 = new Philosopher(eating, 5);
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
		
	}

	public void eating(int idThread){
		
		if(getNumbersFreeLock()<2){
			
				thinking(idThread);
		}
		else{
			if(idThread==1&&philosophersCanEat(idThread, SPOONE_5, SPOONE_1, lock5, lock1)){
				lock5=true;lock1=true;
				System.out.println("Philosopher "+ idThread+ " is eating");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock5=false;
				lock1=false;
			}
			if(idThread==2&&philosophersCanEat(idThread, SPOONE_1, SPOONE_2, lock1, lock2)){
				lock1=true;lock2=true;
				System.out.println("Philosopher "+ idThread+ " is eating");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock1=false;
				lock2=false;
			}
			if(idThread==3&&philosophersCanEat(idThread, SPOONE_2, SPOONE_3, lock2, lock3)){
				lock2=true;lock3=true;
				System.out.println("Philosopher "+ idThread+ " is eating");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock2=false;
				lock3=false;
			}
			if(idThread==4&&philosophersCanEat(idThread, SPOONE_3, SPOONE_4, lock3, lock4)){
				lock3=true;lock4=true;
				System.out.println("Philosopher "+ idThread+ " is eating");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock3=false;
				lock4=false;
			}
			if(idThread==5&&philosophersCanEat(idThread, SPOONE_4, SPOONE_5, lock4, lock5)){
				lock4=true;lock5=true;
				System.out.println("Philosopher "+ idThread+ " is eating");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock4=false;
				lock5=false;
			}
			else{thinking(idThread);}
		}
		
	}
	public void thinking(int id){
		System.out.println("Philosopher "+ id+ " is thinking");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static boolean philosophersCanEat(int id,String firstSpoon,
			String secondSpoon, boolean firstLock, boolean secondLock ){
		if(id==1&&firstSpoon.equals(PhilosophersEating.SPOONE_5)
				&&secondSpoon.equals(PhilosophersEating.SPOONE_1)&&!firstLock&&!secondLock){
			return true;
			}
		if(id==2&&firstSpoon.equals(PhilosophersEating.SPOONE_1)
				&&secondSpoon.equals(PhilosophersEating.SPOONE_2)&&!firstLock&&!secondLock){
			return true;
		}
		if(id==3&&firstSpoon.equals(PhilosophersEating.SPOONE_2)
				&&secondSpoon.equals(PhilosophersEating.SPOONE_3)&&!firstLock&&!secondLock){
			return true;
		}
		if(id==4&&firstSpoon.equals(PhilosophersEating.SPOONE_3)
				&&secondSpoon.equals(PhilosophersEating.SPOONE_4)&&!firstLock&&!secondLock){
			return true;
		}
		if(id==5&&firstSpoon.equals(PhilosophersEating.SPOONE_4)
				&&secondSpoon.equals(PhilosophersEating.SPOONE_5)&&!firstLock&&!secondLock){
			return true;
		}
		return false;
	}
	public int getNumbersFreeLock(){
		numberFreeLock=0;
		if(!lock1){
			numberFreeLock++;
		}
		if(!lock2){
			numberFreeLock++;
		}
		if(!lock3){
			numberFreeLock++;
		}
		if(!lock4){
			numberFreeLock++;
		}
		if(!lock5){
			numberFreeLock++;
		}
		return numberFreeLock;
	}
}


class Philosopher extends Thread{
	private final PhilosophersEating pi;
	private final int number;
	
	public int getNumber() {
		return number;
	}
	public Philosopher(PhilosophersEating pi, int number) {
    this.pi=pi;
    this.number=number;
	}
	@Override
	public void run() {
		while(true){
			pi.eating(getNumber());
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}