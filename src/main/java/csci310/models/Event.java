package csci310.models;

import java.util.*;

public class Event {
    enum Wrapper {
        arr('[',']'),
        obj('{','}'),
        quot('"','"');
        
        final char open,close;
        Wrapper(final char open,final char close) {
            this.open = open;
            this.close = close;
        }
        String unwrap(final String s) {
            return s.substring(s.indexOf(open)+1,s.lastIndexOf(close));
        }
        static Wrapper detect(final String s) {
            for(final char c : s.toCharArray()) {
                if(!Character.isWhitespace(c)) {
                    for(final Wrapper w : values())
                        if(w.open == c)
                            return w;
                    break;
                }
            }
            return null;
        }
        static final char NONE = 0;
        static char getClose(final char c) {
            for(final Wrapper w : values())
                if(w.open == c)
                    return w.close;
            return NONE;
        }
    }
    @FunctionalInterface
    interface Parser {Object parse(String in);}
    enum Type {
        ARR("\\[.*\\]",Event::parseArray),
        BOOL("true|false",Boolean::valueOf),
        OBJ("\\{.*\\}",Event::parseObject),
        QUOT("\".*\"",Event::parseString),
        NUM("^\\d*(?:\\d\\.|\\.\\d|\\d)\\d*(?:[eE][+-]?\\d+)?",Double::parseDouble),
        NULL("null",Event::parseNull);
        
        final String regex;
        final Parser parser;
        Type(final String regex,final Parser parser) {
            this.regex = regex;
            this.parser = parser;
        }
        Object parse(final String in) {return parser.parse(in);}
        static Object parseValue(final String in) {
            for(final Type t : Type.values())
                if(in.matches(t.regex))
                    return t.parse(in.trim());
            return null;
        }
    }
    static final char KV_SPLIT = ':',COMMA_SPLIT = ',';
    static List<String> split(final String in,final char split,final int max) {
        final List<String> out = new ArrayList<>();
        final LinkedList<Character> stack = new LinkedList<>();
        int start = 0,count = 1;
        boolean escaped = false;
        final char[] arr = in.toCharArray();
        if(max != 1)
            for(int i = 0;i < arr.length;++i) {
                if(escaped) escaped = false;
                else {
                    final char c = arr[i];
                    if(!(escaped = c == '\\')) {
                        if(stack.isEmpty() && c == split) {
                            final String o = new String(arr,start,i-start).trim();
                            if(!o.isEmpty()) 
                            	out.add(o);
                            start = i + 1;
                            if(++count == max) break;
                        } else if((Character)c == stack.peek()) stack.pop();
                        else if(stack.peek() != (Character)'"') {
                            final char close = Wrapper.getClose(c);
                            if(close != Wrapper.NONE) stack.push(close);
                        }
                    }
                }
            }
        final String o = new String(arr,start,arr.length-start).trim();
        if(!o.isEmpty()) out.add(o);
        return out;
    }
    static List<String> split(final String in,final char split) {
        return split(in,split,0);
    }
    static List<Object> parseArray(final String in) {
        final List<String> split = split(Wrapper.arr.unwrap(in),COMMA_SPLIT);
        final List<Object> l = new ArrayList<>(split.size());
        for(final String s : split) {
            final Object o = Type.parseValue(s);
            if(o != null)
                l.add(o);
        }
        return l;
    }
    static Map<String,Object> parseObject(final String in) {
        final List<String> s1 = split(Wrapper.obj.unwrap(in),COMMA_SPLIT);
        Map<String,Object> map = new HashMap<>(s1.size());
        for(final String s : s1) {
            final List<String> s2 = split(s,KV_SPLIT,2);
            map.put(
                parseString(s2.get(0)),
                Type.parseValue(s2.get(1))
            );
        }
        return map;
    }
    static String parseString(final String in) {
        return Wrapper.quot.unwrap(in);
    }
    static Object parseNull(final String in) {return null;}
    
    public Object data;
    public Event(final String parse) {data = Type.parseValue(parse);}
}