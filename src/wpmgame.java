import java.time.LocalTime;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
public class wpmgame {
    Scanner sc;
    char choice = 'm';
    int difficulty, chars = 0, words = 0;
    String[] collections;
    String question, answer;
    double time;
    float accuracy, wpm, cps; //they are different
    float[] leaderboard;
    LinkedList<Integer> wll;

    public static void clrscr() {
        try {
        if(System.getProperty("os.name").toLowerCase().contains("win"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void broom() {
        question = "";
        answer = "";
        time = 0;
        chars = 0;
        words = 0;
    }

    public void countdown() throws InterruptedException {
        for (int count = 3; count > 0; count--) {
            System.out.println("NEWGAME");
            System.out.print("   " + count);
            TimeUnit.SECONDS.sleep(1);
            clrscr();
        }
        System.out.println("GO!\n");
    }

    public static void main(String str[]) throws InterruptedException {
        wpmgame obj = new wpmgame();
        obj.menu();
    }

    public void menu() throws InterruptedException {
        while (choice != 'q') {
            if (choice == 'n')
                newgame();
            else if (choice == 'h')
                highscores();
            else if (choice == 'm') {
                clrscr();
                System.out.println("\n\tNEW GAME (\033[44mn\033[0m)\n\tHIGH SCORES (\033[44mh\033[0m)\n\tQUIT (\033[44mq\033[0m)");
                choice = sc.next().charAt(0);
            } else {
                System.out.println("OOPS! INVALID CHOICE");
                choice = sc.next().charAt(0);
            }
        }
        clrscr();
        System.out.println("THANK YOU FOR PLAYING!");
        TimeUnit.SECONDS.sleep(1);
        clrscr();
        return;
    }

    public void newgame() throws InterruptedException {
        clrscr();
        broom();
        String[] wquestion = question();
        countdown();
        System.out.println(question + "\n");
        String[] wanswer = answer();
        calcresult(wquestion, wanswer);
        printresult(wquestion, wanswer);
        highscores();
        choice = sc.next().charAt(0);
    }

    public String[] question() {
        String wquestion[];
        Random rand = new Random();
        wquestion = new String[difficulty];
        for (int i = 0; i < difficulty; i++) {
            wquestion[i] = collections[rand.nextInt(999)];
        }
        for (int i = 0; i < difficulty; i++) question += (wquestion[i] + " ");
        return wquestion;
    }

    public String[] answer() {
        double start, end;
        sc.nextLine(); //to consume nextline
        start = LocalTime.now().toNanoOfDay();
        answer = "";
        answer = sc.nextLine();
        end = LocalTime.now().toNanoOfDay();
        time = ((end - start) / 1000000000.0);
        String[] wanswer = answer.split("\\s+");
        return wanswer;
    }

    public void calcresult(String[] q, String[] a) {
        int k;
        for (int i = 0; i < Math.min(q.length, a.length); i++) {
            for (k = i; a[k] == " "; k++) {
            }
            if (q[i].equals(a[k])) {
                words++;
            }
        }
        chars += words;
    }

    public void printresult(String[] q, String[] a) throws InterruptedException {
        clrscr();
        answer = "";
        System.out.println(question + "\n");
        for (int i = 0; i < Math.min(a.length, q.length); i++) {
            for (int j = 0; j < Math.min(a[i].length(), q[i].length()); j++) {
                answer += a[i].charAt(j);
                TimeUnit.MILLISECONDS.sleep(3);
                if (a[i].charAt(j) == q[i].charAt(j)) {
                    chars++;
                    System.out.print("\033[31;32;1m" + a[i].charAt(j) + "\033[0m");
                } else
                    System.out.print("\033[31;1;4m" + a[i].charAt(j) + "\033[0m");
            }
            System.out.print(" ");
        }
        accuracy = 100 * ((float) chars / (float) question.length());
        wpm = (float)(60 * words) / (float) time;
        cps = (float)chars / (float) time;
        System.out.println("\n\nYour accuracy is " + accuracy + "%");
        System.out.println("Your speed is about " + wpm + " wpm or about " + cps + " cps");
        System.out.println("and you took " + time + " seconds");
    }
    
    public void highscores() {
        if (choice == 'h') {
            clrscr();
            System.out.println("HIGH SCORES");
        }
        if (choice != 'h' && accuracy >= 50.0 && time >= 3.0) {
            int s = (int) (wpm * 100);
            for (int j = 0; j <= wll.size(); j++) {
                if (wll.size() == 0) {
                    wll.add(j, s);
                    break;
                } else if (s <= wll.get(j)) {
                    wll.add(j, s);
                    break;
                } else if (j + 1 == wll.size()) {
                    wll.add(j + 1, s);
                    break;
                }
                if (s <= wll.get(j + 1)) {
                    wll.add(j + 1, s);
                    break;
                }
            }
        }
        if (choice == 'h') {
            for (int i = wll.size()-1; i >= 0; i--) {
            if(i==wll.size()-1)
                System.out.println("\033[33m" + (float) wll.get(i) / 100.0 + "\033[0m 🥇\n");
            else if(i==wll.size()-2)
                System.out.println("\033[36m" + (float) wll.get(i) / 100.0 + "\033[0m 🥈\n");
            else if(i==wll.size()-3)
                System.out.println("\033[35m" + (float) wll.get(i) / 100.0 + "\033[0m 🥉\n");
            else
                System.out.println((float) wll.get(i) / 100.0);
            }
            choice = sc.next().charAt(0);
        }
    }
    
    public wpmgame() {
        wll = new LinkedList<Integer>();
        sc = new Scanner(System.in);
        String[] collection = {"the", "of", "to", "and", "a", "in", "is", "it", "you", "that", "he", "was", "for", "on", "are", "with", "as", "I", "his", "they", "be", "at", "one", "have", "this", "from", "or", "had", "by", "word", "but", "what", "some", "we", "can", "out", "other", "were", "all", "there", "when", "up", "use", "your", "how", "said", "an", "each", "she", "which", "do", "their", "time", "if", "will", "way", "about", "many", "then", "them", "write", "would", "like", "so", "these", "her", "long", "make", "thing", "see", "him", "two", "has", "look", "more", "day", "could", "go", "come", "did", "number", "sound", "no", "most", "people", "my", "over", "know", "water", "than", "call", "first", "who", "may", "down", "side", "been", "now", "find", "any", "new", "work", "part", "take", "get", "place", "made", "live", "where", "after", "back", "little", "only", "round", "man", "year", "came", "show", "every", "good", "me", "give", "our", "under", "name", "very", "through", "just", "form", "sentence", "great", "think", "say", "help", "low", "line", "differ", "turn", "cause", "much", "mean", "before", "move", "right", "boy", "old", "too", "same", "tell", "does", "set", "three", "want", "air", "well", "also", "play", "small", "end", "put", "home", "read", "hand", "port", "large", "spell", "add", "even", "land", "here", "must", "big", "high", "such", "follow", "act", "why", "ask", "men", "change", "went", "light", "kind", "off", "need", "house", "picture", "try", "us", "again", "animal", "point", "mother", "world", "near", "build", "self", "earth", "father", "head", "stand", "own", "page", "should", "country", "found", "answer", "school", "grow", "study", "still", "learn", "plant", "cover", "food", "sun", "four", "between", "state", "keep", "eye", "never", "last", "let", "thought", "city", "tree", "cross", "farm", "hard", "start", "might", "story", "saw", "far", "sea", "draw", "left", "late", "run", "don't", "while", "press", "close", "night", "real", "life", "few", "north", "open", "seem", "together", "next", "white", "children", "begin", "got", "walk", "example", "ease", "paper", "group", "always", "music", "those", "both", "mark", "often", "letter", "until", "mile", "river", "car", "feet", "care", "second", "book", "carry", "took", "science", "eat", "room", "friend", "began", "idea", "fish", "mountain", "stop", "once", "base", "hear", "horse", "cut", "sure", "watch", "color", "face", "wood", "main", "enough", "plain", "girl", "usual", "young", "ready", "above", "ever", "red", "list", "though", "feel", "talk", "bird", "soon", "body", "dog", "family", "direct", "pose", "leave", "song", "measure", "door", "product", "black", "short", "numeral", "class", "wind", "question", "happen", "complete", "ship", "area", "half", "rock", "order", "fire", "south", "problem", "piece", "told", "knew", "pass", "since", "top", "whole", "king", "space", "heard", "best", "hour", "better", "true.", "during", "hundred", "five", "remember", "step", "early", "hold", "west", "ground", "interest", "reach", "fast", "verb", "sing", "listen", "six", "table", "travel", "less", "morning", "ten", "simple", "several", "vowel", "toward", "war", "lay", "against", "pattern", "slow", "center", "love", "person", "money", "serve", "appear", "road", "map", "rain", "rule", "govern", "pull", "cold", "notice", "voice", "unit", "power", "town", "fine", "certain", "fly", "fall", "lead", "cry", "dark", "machine", "note", "wait", "plan", "figure", "star", "box", "noun", "field", "rest", "correct", "able", "pound", "done", "beauty", "drive", "stood", "contain", "front", "teach", "week", "final", "gave", "green", "oh", "quick", "develop", "ocean", "warm", "free", "minute", "strong", "special", "mind", "behind", "clear", "tail", "produce", "fact", "street", "inch", "multiply", "nothing", "course", "stay", "wheel", "full", "force", "blue", "object", "decide", "surface", "deep", "moon", "island", "foot", "system", "busy", "test", "record", "boat", "common", "gold", "possible", "plane", "stead", "dry", "wonder", "laugh", "thousand", "ago", "ran", "check", "game", "shape", "equate", "hot", "miss", "brought", "heat", "snow", "tire", "bring", "yes", "distant", "fill", "east", "paint", "language", "among", "grand", "ball", "yet", "wave", "drop", "heart", "am", "present", "heavy", "dance", "engine", "position", "arm", "wide", "sail", "material", "size", "vary", "settle", "speak", "weight", "general", "ice", "matter", "circle", "pair", "include", "divide", "syllable", "felt", "perhaps", "pick", "sudden", "count", "square", "reason", "length", "represent", "art", "subject", "region", "energy", "hunt", "probable", "bed", "brother", "egg", "ride", "cell", "believe", "fraction", "forest", "sit", "race", "window", "store", "summer", "train", "sleep", "prove", "lone", "leg", "exercise", "wall", "catch", "mount", "wish", "sky", "board", "joy", "winter", "sat", "written", "wild", "instrument", "kept", "glass", "grass", "cow", "job", "edge", "sign", "visit", "past", "soft", "fun", "bright", "gas", "weather", "month", "million", "bear", "finish", "happy", "hope", "flower", "clothe", "strange", "gone", "jump", "baby", "eight", "village", "meet", "root", "buy", "raise", "solve", "metal", "whether", "push", "seven", "paragraph", "third", "shall", "held", "hair", "describe", "cook", "floor", "either", "result", "burn", "hill", "safe", "cat", "century", "consider", "type", "law", "bit", "coast", "copy", "phrase", "silent", "tall", "sand", "soil", "roll", "temperature", "finger", "industry", "value", "fight", "lie", "beat", "excite", "natural", "view", "sense", "ear", "else", "quite", "broke", "case", "middle", "kill", "son", "lake", "moment", "scale", "loud", "spring", "observe", "child", "straight", "consonant", "nation", "dictionary", "milk", "speed", "method", "organ", "pay", "age", "section", "dress", "cloud", "surprise", "quiet", "stone", "tiny", "climb", "cool", "design", "poor", "lot", "experiment", "bottom", "key", "iron", "single", "stick", "flat", "twenty", "skin", "smile", "crease", "hole", "trade", "melody", "trip", "office", "receive", "row", "mouth", "exact", "symbol", "die", "least", "trouble", "shout", "except", "wrote", "seed", "tone", "join", "suggest", "clean", "break", "lady", "yard", "rise", "bad", "blow", "oil", "blood", "touch", "grew", "cent", "mix", "team", "wire", "cost", "lost", "brown", "wear", "garden", "equal", "sent", "choose", "fell", "fit", "flow", "fair", "bank", "collect", "save", "control", "decimal", "gentle", "woman", "captain", "practice", "separate", "difficult", "doctor", "please", "protect", "noon", "whose", "locate", "ring", "character", "insect", "caught", "period", "indicate", "radio", "spoke", "atom", "human", "history", "effect", "electric", "expect", "crop", "modern", "element", "hit", "student", "corner", "party", "supply", "bone", "rail", "imagine", "provide", "agree", "thus", "capital", "won't", "chair", "danger", "fruit", "rich", "thick", "soldier", "process", "operate", "guess", "necessary", "sharp", "wing", "create", "neighbor", "wash", "bat", "rather", "crowd", "corn", "compare", "poem", "string", "bell", "depend", "meat", "rub", "tube", "famous", "dollar", "stream", "fear", "sight", "thin", "triangle", "planet", "hurry", "chief", "colony", "clock", "mine", "tie", "enter", "major", "fresh", "search", "send", "yellow", "gun", "allow", "print", "dead", "spot", "desert", "suit", "current", "lift", "rose", "continue", "block", "chart", "hat", "sell", "success", "company", "subtract", "event", "particular", "deal", "swim", "term", "opposite", "wife", "shoe", "shoulder", "spread", "arrange", "camp", "invent", "cotton", "born", "determine", "quart", "nine", "truck", "noise", "level", "chance", "gather", "shop", "stretch", "throw", "shine", "property", "column", "molecule", "select", "wrong", "gray", "repeat", "require", "broad", "prepare", "salt", "nose", "plural", "anger", "claim", "continent", "oxygen", "sugar", "death", "pretty", "skill", "women", "season", "solution", "magnet", "silver", "thank", "branch", "match", "suffix", "especially", "fig", "afraid", "huge", "sister", "steel", "discuss", "forward", "similar", "guide", "experience", "score", "apple", "bought", "led", "pitch", "coat", "mass", "card", "band", "rope", "slip", "win", "dream", "evening", "condition", "feed", "tool", "total", "basic", "smell", "valley", "nor", "double", "seat", "arrive", "master", "track", "parent", "shore", "division", "sheet", "substance", "favor", "connect", "post", "spend", "chord", "fat", "glad", "original", "share", "station", "dad", "bread", "charge", "proper", "bar", "offer", "segment", "slave", "duck", "instant", "market", "degree", "populate", "chick", "dear", "enemy", "reply", "drink", "occur", "support", "speech", "nature", "range", "steam", "motion", "path", "liquid", "log", "meant", "quotient", "teeth", "shell", "neck"};
        collections = collection.clone();
        choice = 'm';
        difficulty = 20;
        question = "";
        answer = "";
    }
}