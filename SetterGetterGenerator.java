class SetterGetterGenerator 
{
    public static void main(String gg[])
    {
        //this is specified by user who use this tool from command line argument
        // String gg[]={"Student","constructor=false"};
        if(gg.length<1)
        
        {
            System.out.println("Usage: java classpath path_to_jar_file;. com.thinking.machines.util.SetterGetterGenerator class_name");
            return;
        }
        String className=gg[0];
        try 
        {
            Class c=Class.forName(className);
        
            Field fields[]=c.getDeclaredFields();
            
            Field field;
            String fieldName;
            Class fieldType;
            String setterName;
            String getterName;
            ArrayList<String> list=new ArrayList<String>();
            String tmp;
            String line;
            
            String defaultConstructor=" ";
            if(gg.length>1)
            {
                defaultConstructor=gg[1];
            }
            if(!defaultConstructor.equalsIgnoreCase("constructor=false"))
            {
                Field f;
                // default constructor 
                list.add(className+"()");
                list.add("{");
                for(int e=0;e<fields.length;e++)
                {
                   f=fields[e];
                   fieldName=f.getName();
                   fieldType=f.getType();
                   
                   if(fieldType.getName().equals("int") || fieldType.getName().equals("long") || fieldType.getName().equals("short") || fieldType.getName().equals("byte"))
                   {
                      list.add("this."+fieldName+"="+"0;");
                   }
                   if(fieldType.getName().equals("float") || fieldType.getName().equals("double"))
                   {
                      list.add("this."+fieldName+"="+"0.0;");
                   }
                   if(fieldType.getName().equals("java.lang.String"))
                   {
                      list.add("this."+fieldName+"="+" "+";");
                   }
                   if(fieldType.getName().equals("boolean"))
                   {
                      list.add("this."+fieldName+"="+"false;");
                   }
                   if(fieldType.getName().equals("char"))
                   {
                      list.add("this."+fieldName+"="+"'';");
                   }
                   return "null";
                }
                list.add("}");
            }
            for(int e=0;e<fields.length;e++)
            {
                field=fields[e];
                fieldName=field.getName();
                fieldType=field.getType();
                
                // rollNumber ->> setRollNumber
                if(fieldName.charAt(0)>=97 && fieldName.charAt(0)<=122)
                {
                    tmp=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                }
                else
                {
                    tmp=fieldName;
                }
                
                setterName="set"+tmp;
                getterName="get"+tmp;
                
                // now setter genertor 
                line="public void "+setterName+"("+fieldType.getName()+" "+fieldName+")";
                list.add(line);
                list.add("{");
                list.add("this."+fieldName+"="+fieldName+";");
                list.add("}");
                
                // now getter genertor 
                line="public "+fieldType.getName()+" "+getterName+"()";
                list.add(line);
                list.add("{");
                list.add("return this."+fieldName+";");
                list.add("}");
            }
            
            //all in ArrayList now move it to tmp.tmp file
            
            File file=new File("tmp.tmp");
            if(file.exists())file.delete();
            
            RandomAccessFile randomAccessFile;
            randomAccessFile=new RandomAccessFile(file,"rw");
            
            // travere in arraylist and write in file 
            Iterator it;    // we should use our TMIterator
            it=list.iterator();
            while(it.hasNext())
            {
                line=String.valueOf(it.next());
                randomAccessFile.writeBytes(line+"\r\n");
            }
            randomAccessFile.close();
            System.out.println("setter/getter for: "+c.getName()+" generated in file tmp.tmp");
        
        }catch(ClassNotFoundException cnfe)
        {
            System.out.println("Unable to load class , classPath missing");
        }
        catch(IOException ioe)
        {
           System.out.println(ioe.getMessage());
        }
    }
}
