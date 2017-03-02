import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Demo {
	
	static Card[] play1Hand;
	static Card[] play2Hand;
	static int[] play1CardArr;
	static int[] play2CardArr;
	int play1Rank;
	int play2Rank;
	static int play1Win = 0;
	static int play2Win = 0;

	public static void main(String[] args) {
		String thisLine = null;
	      
	    try {
	      
//	    	FileInputStream fis = new FileInputStream(args[0]);
//	  		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//	         
//	         while ((thisLine = br.readLine()) != null) {
//	        	 calculate(thisLine);
//	         }       
	         
	    	calculate("4D 6S 9H QH QC 3D 6D 7H QD QS");
	    	System.out.println("Player 1: "+play1Win);
	    	System.out.println("Player 2: "+play2Win);
	    } catch(Exception e) {
	         e.printStackTrace();
	    }

	}
	
	static void calculate(String line){
		int tmpNum;
		int tmpSuit;
		play1Hand = new Card[5];
		play2Hand = new Card[5];
		play1CardArr = new int[14];
		play2CardArr = new int[14];
		String[] cardString = line.split("\\s");
		
		//locate cards
		for(int i=0;i<10;i++){
			String tmpNumString = Character.toString(cardString[i].charAt(0));
			String tmpSuitString = Character.toString(cardString[i].charAt(1));
			//convert to number
			tmpNum = isInteger(tmpNumString,10)?Integer.valueOf(tmpNumString):numAndSuitEnum.valueOf(tmpNumString).getNum();
			tmpSuit = isInteger(tmpSuitString,10)?Integer.valueOf(tmpSuitString):numAndSuitEnum.valueOf(tmpSuitString).getNum();
			
			if(i<5){
				play1Hand[i] = new Card(tmpNum,tmpSuit);
				play1CardArr[tmpNum]++;
			}else{
				play2Hand[i%5] = new Card(tmpNum,tmpSuit);
				play2CardArr[tmpNum]++;
			}
			
		}
		int play1Rank = calculateRank(play1CardArr,play1Hand);
		int play2Rank = calculateRank(play2CardArr,play2Hand);
		
		if(play1Rank==play2Rank){
			boolean play1WinBoolean = calculateSuit(play1Rank,play1CardArr,play1Hand,play2CardArr,play2Hand);
			if(play1WinBoolean){
				play1Win++;
			}else{
				play2Win++;
			}
		}else if(play1Rank > play2Rank){
			play1Win++;
		}else{
			play2Win++;
		}
		
	} 
	
	//calculate Rank of cards
	static int calculateRank(int[] cardArr,Card[] card){
		//accumulate the same number of cards
		int[] accumArr = new int[5];
		for(int i=1;i<14;i++){
			if(cardArr[i]==0) continue;
			accumArr[cardArr[i]]++;
		}
		
		//Four of a kind
		if(accumArr[4]==1){return 8;}
			
		//Full house	
		else if(accumArr[3]==1 && accumArr[2]==1){return 7;}
			
		//Three of a kind
		else if(accumArr[3]==1){return 4;}
			
		//Two pairs
		else if(accumArr[2]==2){return 3;}
			
		//Two pairs
		else if(accumArr[2]==1){return 2;}
			
		else{
			//the same suit
			if (card[0].suit == card[1].suit && card[1].suit == card[2].suit && card[2].suit == card[3].suit &&
					card[3].suit == card[4].suit && card[4].suit == card[0].suit){
				
				//Royal Flush
				if(cardArr[10]==1&&cardArr[11]==1&&cardArr[12]==1&&cardArr[13]==1&&cardArr[1]==1){return 10;}
				
				for(int i=1;i<14;i++){
					if(cardArr[i]==0) continue;
					//Straight flush
					if(cardArr[i]==1&&cardArr[i+1]==1&&cardArr[i+2]==1&&cardArr[i+3]==1&&cardArr[i+4]==1){return 9;}
					else{break;}
				}
				
				//Flush
				return 6;	
			}else{
				//Straight
				if(cardArr[10]==1&&cardArr[11]==1&&cardArr[12]==1&&cardArr[13]==1&&cardArr[1]==1){return 5;}
				for(int i=1;i<14;i++){
					if(cardArr[i]==0) continue;
					//Straight 
					if(cardArr[i]==1&&cardArr[i+1]==1&&cardArr[i+2]==1&&cardArr[i+3]==1&&cardArr[i+4]==1){return 5;}
					else{break;}
				}
				
				//High card
				return 1;	
				
			}
		}
	}
	
	//when the same rank calculate suit of cards
	static boolean calculateSuit(int rank,int[] play1CardArr,Card[] play1Card,int[] play2CardArr,Card[] play2Card){
		int play1Num = 0;
		int play2Num = 0;
		int play1Suit = 0;
		int play2Suit = 0;
		if(rank==10){return play1Card[0].suit > play2Card[0].suit;}
		
		else if(rank==1 || rank==9){
			for(int i=13;i>0;i--){
				if(play1CardArr[i]==0){continue;}
				else{
					if(play1CardArr[1]==1){i=14;}
					play1Num=i;break;
				}
			}
			for(int i=13;i>0;i--){
				if(play2CardArr[i]==0){continue;}
				else{
					if(play2CardArr[1]==1){i=14;}
					play2Num=i;break;
				}
			}
			if(play1Num==play2Num){
				if(play1Num==14){play1Num=1;play2Num=1;}
				play1Suit = 0; play2Suit = 0; 
				for(int j=0;j<5;j++){
					if(play1Card[j].num==play1Num && play1Suit<play1Card[j].suit){play1Suit=play1Card[j].suit;}
					if(play2Card[j].num==play2Num && play2Suit<play2Card[j].suit){play2Suit=play2Card[j].suit;}
				}
				return play1Suit > play2Suit;
			}else{
				return play1Num > play2Num;
			}
		}
		
		else if(rank==8){
			for(int i=13;i>0;i--){
				if(play1CardArr[i]==0){continue;}
				if(play1CardArr[i]==4){
					if(i==1){i=14;}
					play1Num=i;break;
				}
			}
			for(int i=13;i>0;i--){
				if(play2CardArr[i]==0){continue;}
				if(play2CardArr[i]==4){
					if(i==1){i=14;}
					play2Num=i;break;
				}
			}
			if(play1Num==play2Num){
				if(play1Num==14){play1Num=1;play2Num=1;}
				play1Suit = 0; play2Suit = 0; 
				for(int j=0;j<5;j++){
					if(play1Card[j].num==play1Num && play1Suit<play1Card[j].suit){play1Suit=play1Card[j].suit;}
					if(play2Card[j].num==play2Num && play2Suit<play2Card[j].suit){play2Suit=play2Card[j].suit;}
				}
				return play1Suit > play2Suit;
			}else{
				return play1Num > play2Num;
			}
		}
		
		else if(rank==4 || rank==7){
			for(int i=13;i>0;i--){
				if(play1CardArr[i]==0){continue;}
				if(play1CardArr[i]==3){
					if(i==1){i=14;}
					play1Num=i;break;
				}
			}
			for(int i=13;i>0;i--){
				if(play2CardArr[i]==0){continue;}
				if(play2CardArr[i]==3){
					if(i==1){i=14;}
					play2Num=i;break;
				}
			}
			return play1Num > play2Num;
		}
		
		else if(rank==5){
			for(int i=13;i>0;i--){
				if(play1CardArr[i]==0){continue;}
				if(play1CardArr[i]==1){
					if(play1CardArr[1]==1){i=14;}
					play1Num=i;break;
				}
			}
			for(int i=13;i>0;i--){
				if(play2CardArr[i]==0){continue;}
				if(play2CardArr[i]==1){
					if(play2CardArr[1]==1){i=14;}
					play2Num=i;break;
				}
			}
			if(play1Num==play2Num){
				if(play1Num==14){play1Num=1;play2Num=1;}
				play1Suit = 0; play2Suit = 0;  
				for(int j=0;j<5;j++){
					if(play1Card[j].num==play1Num && play1Suit<play1Card[j].suit){play1Suit=play1Card[j].suit;}
					if(play2Card[j].num==play2Num && play2Suit<play2Card[j].suit){play2Suit=play2Card[j].suit;}
				}
				return play1Suit > play2Suit;
			}else{
				return play1Num > play2Num;
			}
		}
		
		else if(rank==6){
			for(int i=13;i>0;i--){
				if(play1CardArr[i]==0){continue;}
				if(play1CardArr[i]==1){
					if(play1CardArr[1]==1){i=14;}
					play1Num=i;break;
				}
			}
			for(int i=13;i>0;i--){
				if(play2CardArr[i]==0){continue;}
				if(play2CardArr[i]==1){
					if(play2CardArr[1]==1){i=14;}
					play2Num=i;break;
				}
			}
			if(play1Num==play2Num){
				int index = play1Num==14 ? 13 : play1Num-1;
				play1Num = 0; play2Num = 0; 
				for(int i=index;i>0;i--){
					if(play1CardArr[i]==0 && play2CardArr[i]==0){continue;}
					//tie
					else if(play1CardArr[i]==1 && play2CardArr[i]==1){continue;}
					else if(play1Num==0 && play1CardArr[i]==1){play1Num=i;}
					else if(play2Num==0 && play2CardArr[i]==1){play2Num=i;}
					else if(play1Num!=0 && play2Num!=0){return play1Num > play2Num;}
				}
			}else{
				return play1Num > play2Num;
			}
		}
		
		else if(rank==2 || rank==3){
			for(int i=13;i>0;i--){
				if(play1CardArr[i]==0){continue;}
				if(play1CardArr[i]==2){
					if(play1CardArr[1]==2){i=14;}
					play1Num=i;break;
				}
			}
			for(int i=13;i>0;i--){
				if(play2CardArr[i]==0){continue;}
				if(play2CardArr[i]==2){
					if(play2CardArr[1]==2){i=14;}
					play2Num=i;break;
				}
			}
			if(play1Num==play2Num){
				if(play1Num==14){play1Num=1;play2Num=1;}
				play1Suit = 0; play2Suit = 0; 
				for(int j=0;j<5;j++){
					if(play1Card[j].num==play1Num && play1Suit<play1Card[j].suit){play1Suit=play1Card[j].suit;}
					if(play2Card[j].num==play2Num && play2Suit<play2Card[j].suit){play2Suit=play2Card[j].suit;}
				}
				return play1Suit > play2Suit;
			}else{
				return play1Num > play2Num;
			}
		}
		return true;
	}
	
	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}

}
