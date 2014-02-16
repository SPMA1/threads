package tp;

import domain.Task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Nicolas Zozol for Robusta Code
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
public class Calcul {

	int resultA = 0;
	int resultB = 0;

	public int halfSuite(final int a, final int b) throws InterruptedException {

		resultA = 0;
		resultB = 0;
		Thread t1 = new Thread() {

			// somme de 0 à  a : 1+2+3+...+a
			public void run() {
				for (int i = a; i > 0; i--) {
					resultA = resultA + i;
				}

			}
		};

		// //somme de 0 Ã  b : 1+2+3+...+b
		Thread t2 = new Thread() {
			public void run() {
				for (int i = b; i > 0; i--) {
					resultB = resultB + i;
				}
			}
		};

		t1.start();
		t2.start();
		t1.join();
		t2.join();
		return (resultA + resultB) / 2;
	}

	public int halfSuiteWithWait(final int a, final int b)
			throws InterruptedException {

		resultA = 0;
		resultB = 0;

		final Object lock = new Object();

		Thread t1 = new Thread() {
			@Override
			public void run() {
				// somme de 0 à  a : 1+2+3+...+a
				for (int i = a; i > 0; i--) {
					resultA = resultA + i;
				}

				synchronized (lock) {

					if (resultB > 0) {
						// wait or notify ?
						lock.notify();
					} else {
						 //wait or notify ?
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				Task task = new Task("Tweeting for A");
			}
		};

		// //somme de 0 Ã  b : 1+2+3+...+b
		Thread t2 = new Thread() {
			@Override
			public void run() {
				// somme de 0 Ã  b : 1+2+3+...+b
				for (int i = b; i > 0; i--) {
					resultB = resultB + i;
				}

				synchronized (lock) {

					if (resultA > 0) {
						// wait or notify ?
						lock.notify();
					} else {
						// wait or notify ?
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				Task task = new Task("Tweeting for B");
			}
		};

		t1.start();
		t2.start();
		// no join here ! Don't want to wait the end of tweets

		// but a condition
		synchronized (lock) {
			while (resultA == 0 && resultB == 0) {
				lock.wait();
			}
		}

		return (resultA + resultB) / 2;
	}

	public int whatHappensThere(final int a, final int b) throws InterruptedException {

		resultA = 0;
		resultB = 0;

		final Object lock = new Object();
		final Object lock2 = new Object();

		Thread t1 = new Thread() {
			@Override
			public void run() {
				// On met la somme dans le synchronized pluto qu'en dehors
				synchronized (lock) {
					// somme de 0 Ã  a : 1+2+3+...+a
					for (int i = a; i > 0; i--) {
						resultA = resultA + i;
					}
					if (resultB > 0) {
						// wait or notify ?
						lock.notify();
					} else {
						// wait or notify ?
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};

		// //somme de 0 Ã  b : 1+2+3+...+b
		Thread t2 = new Thread() {
			@Override
			public void run() {

				// On met la somme dans le synchronized pluto qu'en dehors
				synchronized (lock2) {
					// somme de 0 à  b : 1+2+3+...+b
					for (int i = b; i > 0; i--) {
						resultB = resultB + i;
					}
					if (resultA > 0) {
						// wait or notify ?
						lock2.notify();
					} else {
						// wait or notify ?
						try {
							lock2.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};

		t1.start();
		t2.start();
		
		synchronized (lock2) {
			while (resultA == 0 && resultB == 0) {
				lock2.wait();
			}
		}
		return (resultA + resultB) / 2;
	}

	public int future(int a, int b) throws Exception {

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		CallableCalcul ca = new CallableCalcul(a);
		CallableCalcul cb = new CallableCalcul(b);

//		ca.call();
//		cb.call();
		
		Future<Integer> fA = executorService.submit(ca);
		// stuff with B
		Future<Integer> fB = executorService.submit(cb);

		
//		notifyAll();
//		fB.notify();
		int resultA = fA.get();

		// stuff with B
		int resultB = fB.get();
		
		// bad result
		return (resultA + resultB) / 2;

	}

}
