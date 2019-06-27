public class Bank {
    ThreadLocal<Integer> t = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 100;
        }
    };

    public int get() {
        return t.get();
    }

    public void set() {
        t.set(t.get() + 10);
    }
}

class Transfer implements Runnable {
    Bank bank;

    public Transfer(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            bank.set();
            System.out.println(Thread.currentThread() + "  " + bank.get());
        }
    }
}
