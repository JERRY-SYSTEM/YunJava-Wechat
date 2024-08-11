public String read(String FilePath) {
    try {
        HashMap FileMemCache = new HashMap();
        if (FileMemCache.containsKey(FilePath)) {
            return FileMemCache.get(FilePath);
        }
        File file = new File(FilePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader bf = new BufferedReader(inputReader);
        StringBuilder Text = new StringBuilder();
        int lineNumber = 1;
        String str;
        while ((str = bf.readLine()) != null) {
            Text.append(lineNumber).append("、").append(str).append("\n");
            lineNumber++;
        }
        if (Text.length() > 0) {
            Text.setLength(Text.length() - 1);
        }
        if (Text.toString().isEmpty()) {
            return "还没有添加";
        }
        FileMemCache.put(FilePath, Text.toString());
        return Text.toString();
    } catch (IOException ioe) {
        return "列表错误";
    }
}






public static void 简写(File ff, String a){
f=new FileWriter(ff,true);
f1=new BufferedWriter(f);
f1.append(a);
f1.newLine();
f1.close();
f.close();
}

public static void 简弃(File ff,String a){
ArrayList l1= new ArrayList();
l1.addAll(简取(ff));
if(l1.contains(a)){
l1.remove(a);
}
f=new FileWriter(ff);
f1=new BufferedWriter(f);
f1.write("");
f1.close();
f.close();
for(int i=0;i<l1.size();i++){
简写(ff,l1.get(i));
}
}
public String 论(String u,String a,String b)
{
return u.replace(a,b);
}
public static ArrayList 简取(File ff){
if(!ff.exists()){
ff.createNewFile();}
fr  =  new  FileReader(ff);  
f2=new BufferedReader(fr);
String a;
FileReader reader = new FileReader(ff);
BufferedReader bReader = new BufferedReader(reader);
ArrayList list1 = new ArrayList();
while ((a = bReader.readLine()) != null) {
list1.add(a);}
fr.close();
f2.close();
return list1;
}






public static void 全弃(File ff){
f=new FileWriter(ff);
f1=new BufferedWriter(f);
f1.write("");
f1.close();
f.close();
}
public boolean 模糊判断(String a,ArrayList l1){
boolean x=false;
for(int i=0;i<l1.size();i++){
if(a.contains(l1.get(i).toString())){
x=true; break;}
}
return x;}
public boolean 简读用户(File ff,String qq)
{
if(简取(ff).contains(qq))return true;
else return false;
}


public boolean 精准判断(String a,ArrayList l1){
for(String element:l1){
if(a.equals(element)){
return true;
}
}
return false;
}
