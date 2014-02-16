package tp;


import static org.junit.Assert.*;

import org.junit.Test;

import tp.Calcul;

import java.util.concurrent.ExecutionException;

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
public class CalculTest {



    @Test
    public void testHalfSuite() throws Exception {

        Calcul calcul = new Calcul();
        int a = 5;//15
        int b =3;//6

        assertTrue(calcul.halfSuite(a, b)==10);

    }

    @Test
    public void testHalfSuiteWithWait() throws Exception {

        Calcul calcul = new Calcul();
        int a = 5;//15
        int b =3;//6

        long time = System.currentTimeMillis();

        assertTrue(calcul.halfSuiteWithWait(a, b)==10);

        long duration = System.currentTimeMillis()-time;
        assertTrue(duration < 100);
    }

    @Test
    public void testWhatHappensThere() throws Exception {

        Calcul calcul = new Calcul();
        int a = 5;//15
        int b =3;//6

        assertTrue(calcul.whatHappensThere(a, b)==10);
    }

    @Test
    public void futureTest() throws Exception {


        Calcul calcul = new Calcul();
        int a = 5;//15
        int b =3;//6

        assertTrue(calcul.future(a, b)==10);
    }
}

