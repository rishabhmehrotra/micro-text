package preprocessing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.UUID;

import pilot.CommanderInChief;

import datastr.*;
/* This class will use the Tweets arraylist of Dataset
 * and build a dictionary of all words found in the Tweets for this dataset
 */
@SuppressWarnings("unused")
public class Dictionary {
	
	public Dictionary(Dataset dataset)
	{
		int i,j,k,l,m,n;
		int len=dataset.Tweets.size();
		Iterator<Tweet> itr=dataset.Tweets.iterator();
		int tempidf=0;int twno=0;
		int index=0;
		int counter=0;
		while(itr.hasNext())
		{
			//now deal with each tweet and insert the words in each tweet in Dictionary if its not present already
			tempidf=0;twno++;int flag=0;
			Tweet tw=itr.next();
			ArrayList<Word> list=tw.getWordList();
			Iterator<Word> wrditr=list.iterator();
			Word tempw;tempidf=0;
			//for each word in this particular tweet modify the dictionary & wcl.list accordingly
		
			while(wrditr.hasNext())
			{
				Word w=wrditr.next();
				w.word=w.word.trim();
				w.word=w.word.toLowerCase();
				if(w.word.length()>1)
				{
					//---------------------------------------------insert in Dictionary start---------------------------------------
					if(dataset.dictionary.containsKey(w.word)) 
					{
						
						tempw=dataset.dictionary.get(w.word);
						if(tempw.index==0) {System.out.println("something wrong!!");System.exit(0);}
						tempw.count+=w.count;
						tempw.idf++;
						dataset.dictionary.put(tempw.word, tempw);//added this on 6th June 2012
					}
					else
					{ 
						//if(index==0) System.out.println("anamoly counter ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" +counter++);
						//System.exit(0);
						w.id=UUID.randomUUID();
						w.index=index++;
						System.out.println("Index = _"+index);
						dataset.dictionary.put(w.word, w);
					}
					//---------------------------------------------insert in Dictionary end---------------------------------------					
				}
			}
		}
		if(CommanderInChief.DEBUG_MODE == 1)
			System.out.println("Total Dictionary elements Count= "+dataset.dictionary.size());
		// our dictionary has been populated now...Yay!
		generateBOWM(dataset);
	}
	
	public void generateBOWM(Dataset dataset){
		int totalUniqueWordCount = dataset.dictionary.size();
	
		Iterator<Tweet> iter=dataset.Tweets.iterator();
		int bagOfWordsRep[] = new int [totalUniqueWordCount];
		while(iter.hasNext())
		{
			Arrays.fill(bagOfWordsRep, 0);
			Tweet tw=(Tweet) iter.next();
			ArrayList<Word> wrdlst=tw.getWordList();
			Iterator<Word> it=wrdlst.iterator();
		
			//System.out.println(totalUniqueWordCount);
			
				while(it.hasNext())
					{
					Word wt=(Word) it.next();
					if(dataset.dictionary.containsKey(wt.word)){
						bagOfWordsRep[wt.index]=1;
						System.out.println(wt.word + wt.index);
						System.out.println("one written");
						}
					}
			 tw.setBagOfWordsRepresentation(bagOfWordsRep);
			 
			 
		}
	}
}

