public class Main {

    public static void main(String[] args)
    {
        BTernary a=new BTernary("+++0-");
        System.out.println( );
        BTernary c=new BTernary("-0+");

        BTernary sum = a.add(c);
        BTernary sub=a.sub(c);


        System.out.println("sum= "+sum + "; sub= "+ sub);
    }


    public static class BTernary
    {
        String value;
        public BTernary(String s)
        {
            if(s.length()>16 || s.isEmpty()){
                throw new IllegalArgumentException();
            }
            int i=0;
            //избавляемся от 0 в начале строки
            while(s.charAt(i)=='0')
                i++;
            this.value=s.substring(i);
        }


        public BTernary add(BTernary that)
        {
            String a=this.value;
            String b=that.value;

            String longer=a.length()>b.length()?a:b;
            String shorter=a.length()>b.length()?b:a;

            //приводим значения к одной длине
            while(shorter.length()<longer.length())
                shorter=0+shorter;

            a=longer;
            b=shorter;

            //храним остаток
            char carry='0';
            String sum="";
            for(int i=0;i<a.length();i++)
            {
                //разряд на котором осуществляется сложение
                int place=a.length()-i-1;
                // сумма, которую получили после сложения разрядов
                String digitSum=addDigits(a.charAt(place),b.charAt(place),carry);
                //условие когда у нас +- или -+
                if(digitSum.length()!=1)
                    carry=digitSum.charAt(0);
                else
                    carry='0';
                sum=digitSum.charAt(digitSum.length()-1)+sum;
            }
            sum=carry+sum;

            return new BTernary(sum);
        }
        private String addDigits(char a,char b,char carry)
        {
            //сумма разрядов
            String sum1=addDigits(a,b);
            //прибавляем перенос ( если у нас sum1=-+ , то - уходит как остаток на следующую итерацию сложения,
            // а + мы складываем с остатком, если он есть на текущей итерации сложения разрядов
            String sum2=addDigits(sum1.charAt(sum1.length()-1),carry);
            if(sum1.length()==1)
                return sum2;
            if(sum2.length()==1)
                return sum1.charAt(0)+sum2;
            return sum1.charAt(0)+"";
        }
        private String addDigits(char a,char b)
        {
            String sum="";
            if(a=='0')
                sum=b+"";
            else if (b=='0')
                sum=a+"";
            else if(a=='+')
            {
                if(b=='+')
                    sum="+-";
                else
                    sum="0";
            }
            else
            {
                if(b=='+')
                    sum="0";
                else
                    sum="-+";
            }
            return sum;
        }

        //преобразуем число в отрицательное, путем проебразования каждого разряда на противоположный
        public BTernary neg()
        {
            return new BTernary(invert(this.value));
        }

        // меняем значения на противположное
        private String invert(String s)
        {
            String invertValue="";
            for(int i=0;i<s.length();i++)
            {
                if(s.charAt(i)=='+')
                    invertValue+='-';
                else if(s.charAt(i)=='-')
                    invertValue+='+';
                else
                    invertValue+='0';
            }
            return invertValue;
        }

        public BTernary sub(BTernary that)
        {
            return this.add(that.neg());
        }

        public String toString()
        {
            return value;
        }
    }
}
