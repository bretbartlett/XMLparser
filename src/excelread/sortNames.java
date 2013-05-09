/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package excelread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import excelread.Excelread;
import excelread.Excelread.INGRED;

/**
 *
 * @author bretbartlett
 */

public class sortNames {
   
   public static List<INGRED> SortIngredients (List<INGRED> Ingredients){
       
       List<INGRED> SortedIngs = new ArrayList<INGRED>();  
       boolean finished = false;
       boolean there = false;
       
       for( INGRED input : Ingredients){
         for(INGRED createdList : SortedIngs){  
             if(createdList.getIng().equals(input.getIng())){
                  there = true;  
             }
         }
         if(!there){
             SortedIngs.add(input);
         }
         there = false;
       }
       
       
       
             
       do{
           finished = true;
           for (INGRED outside : SortedIngs){
               for (INGRED inside : SortedIngs){
                   if (inside.getIng().contains(outside.getIng()) && (SortedIngs.indexOf(inside) > SortedIngs.indexOf(outside))){
                     Collections.swap(SortedIngs, SortedIngs.indexOf(outside), SortedIngs.indexOf(inside));
                     finished = false;
                   }   
            }
        }
       }while(finished);
       
       
       return SortedIngs;
   }
   
  
    
    
    
}
