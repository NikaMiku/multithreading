import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }
        long startTs = System.currentTimeMillis();
        Runnable logic = () -> {
        for (String text: texts) {
                int maxSize = 0;
            for (int i = 0; i < text.length(); i++) {
                for (int j = 0; j < text.length(); j++) {
                    if(i >= j) {
                        continue;
                    }
                    boolean bFound = false;
                    for (int k = i; k < j; k++) {
                        if(text.charAt(k) == 'b') {
                            bFound = true;
                            break;
                        }
                    }
                    if(!bFound && maxSize < j - i) {
                        maxSize = j - i;
                    }
                }
            }
            System.out.println(text.substring(0,100) + " -> " + maxSize);
            }
        };
        Thread thread1 = new Thread(logic);
        Thread thread2 = new Thread(logic);
        Thread thread3 = new Thread(logic);
        Thread thread4 = new Thread(logic);
        threads.add(thread1);
        threads.add(thread2);
        threads.add(thread3);
        threads.add(thread4);
        for (Thread thread : threads) {
            thread.start();
        }
        long endTs = System.currentTimeMillis();
        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int lenght) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < lenght; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}