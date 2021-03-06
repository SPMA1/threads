package tp;

/**
 * Created by  Nicolas Zozol for Robusta Code
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class RunnableCalcul {

    int resultA=0;
    int resultB=0;

    public int halfSuite(int a, int b) throws InterruptedException {


        Object lock = new Object();
        MaxRunnable maxRunnableA = new MaxRunnable(a, lock);
        MaxRunnable maxRunnableB = new MaxRunnable(b, lock);

        maxRunnableA.setFriend(maxRunnableB);
        maxRunnableB.setFriend(maxRunnableA);

        Thread t1 = new Thread(maxRunnableA);
        Thread t2 = new Thread(maxRunnableB);

        t1.start();
        t2.start();

        synchronized (lock){
            lock.wait();
        }

        return (maxRunnableA.getResult()+maxRunnableB.getResult())/2;
    }

}
