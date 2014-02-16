package concurrency.waitnotify;

/**
 * Created by Nicolas
 * Date: 09/09/13
 * Time: 13:28
 */
public class CommentApplication {

    public static void main(String[] args) {

        final CommentWriter writer = new CommentWriter();
        final CommentCounter counter = new CommentCounter();

        Thread writerThread = new Thread() {
            @Override
            public void run() {
                System.out.println("Starting writerThread");
                writer.slowlyWriteComment("I'm in the box");
            }
        };

        
        
        Thread counterThread = new Thread(){
            @Override
            public void run() {
                System.out.println("Starting counterThread");
                try {
                    synchronized (writer){
                        System.out.println("Counter waits gently()");
                        writer.wait();
                        System.out.println("we have " + counter.fastCount() + " comments");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

//        Thread counterThread = new Thread() {
//            @Override
//            public void run() {
//
//                System.out.println("looks we have " + counter.fastCount() + " comments");
//
//
//
//            }
//        };

        writerThread.start();
        counterThread.start();

    }
}


/*
 Thread counterThread = new Thread(){
            @Override
            public void run() {
                System.out.println("Starting counterThread");
                try {
                    synchronized (writer){
                        System.out.println("Counter waits gently()");
                        writer.wait();
                        System.out.println("we have " + counter.fastCount() + " comments");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

==>>> Add notify() in CommentWriter.slowlyWriteComment()
*/