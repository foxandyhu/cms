import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.TestThreadLocal;

public class TT2 {

    public static void main(String[] args) {

        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j=0;j<10;j++){
                        IpThreadLocal.set(IpThreadLocal.get() + 1);
                        System.out.println(Thread.currentThread().getId()+" >> "+ IpThreadLocal.get());
                    }
                }
            });
            thread.start();
        }

//        Bank bank=new Bank();
//        Transfer transfer=new Transfer(bank);
//        Thread t1=new Thread(transfer);
//        t1.start();
//
//        Thread t2=new Thread(transfer);
//        t2.start();
//
//        System.out.println(bank.get()+">>>>>>>>>");
    }
}
