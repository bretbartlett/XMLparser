/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlingredientnormalizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author bretbartlett
 */
public class LIDIAlgo {
        
    public static void main(List <LIDI> Alpha,List <LIDI> Beta, List <LIDI> Rho,List <LIDI> Gamma , int numberofrecipes) throws BiffException, IOException, WriteException {
        
        List <String> LookForThis = new ArrayList<String>();
        
       
         WritableWorkbook workbook = Workbook.createWorkbook(new FileOutputStream("LIDI.xls"));       
         WritableSheet sheet0 = workbook.createSheet("sheet0", 0);
            
             
     int lineNUM = 0;  
     
     Label header = new Label(0, lineNUM, "                  ");
     sheet0.addCell(header);
     Label header1 = new Label(1, lineNUM, "Number of Recipes");
     sheet0.addCell(header1);
     Label header2 = new Label(2, lineNUM, "Average Rating");
     sheet0.addCell(header2);    
     Label header3 = new Label(3, lineNUM, "PMI");
     sheet0.addCell(header3);
     Label header6 = new Label(4, lineNUM, "PMI with total");
     sheet0.addCell(header6);
     Label header4 = new Label(6, lineNUM, "Total Recipes");
     sheet0.addCell(header4);    
     Label header5 = new Label(7, lineNUM, String.valueOf(numberofrecipes));
     sheet0.addCell(header5);    
     lineNUM++;
     
     
     /////////////////////////////////////////////////////////////////////////////////
     ///////  Decide here what recipes to look for, here I look for recipes ///////////
     //////////////  with ids numbered 50 to 100 //////////////////////////////////////
     /////////////////////////////////////////////////////////////////////////////////
     
     for (int i = 50; i < 100 ; i++){
            LookForThis.add(String.valueOf(i));
       }
        
            
            
            
        for(String id : LookForThis){
                        
            System.out.println(id);
            
            List<String> MatchingAlphas = new ArrayList<String>();
            List<String> MatchingBetas = new ArrayList<String>();
            List<String> MatchingGammas = new ArrayList<String>();
            List<String> MatchingRhos = new ArrayList<String>();
            
            List<LIDI> RecipesWithAlpha = new ArrayList<LIDI>();
            List<LIDI> RecipesWithBeta = new ArrayList<LIDI>();
            List<LIDI> RecipesWithGamma = new ArrayList<LIDI>();
            List<LIDI> RecipesWithRho = new ArrayList<LIDI>();
            
            List<String> RecipeIDalphas = new ArrayList<String>();
            List<String> RecipeIDbetas = new ArrayList<String>();
            List<String> RecipeIDgammas = new ArrayList<String>();
            List<String> RecipeIDrhos = new ArrayList<String>();
            
            
            /////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////
            
            for(LIDI Alphas : Alpha){
                if(Alphas.recipeid.equals(id)){
                    MatchingAlphas.add(Alphas.ingredient);
                }
            }
          for (LIDI Alphas : Alpha){
              for(String AlphaSearch : MatchingAlphas){ 
                  if(Alphas.ingredient.equals(AlphaSearch) && !(RecipeIDalphas.contains(Alphas.recipeid)) ){
                            RecipesWithAlpha.add(Alphas);
                            RecipeIDalphas.add(Alphas.recipeid);
                     }
                }
            }     
            
            /////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////
            
            
            for(LIDI Betas : Beta){
                if(Betas.recipeid.equals(id)){
                    MatchingBetas.add(Betas.ingredient);
                }
            }
             for (LIDI Betas : Beta){
                for(String BetaSearch : MatchingBetas){ 
                  if(Betas.ingredient.equals(BetaSearch) && !(RecipeIDbetas.contains(Betas.recipeid)) ){
                            RecipesWithBeta.add(Betas);
                            RecipeIDbetas.add(Betas.recipeid);
                     }
                }
            }     
            
            /////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////
            
            
            for(LIDI Gammas : Gamma){
                if(Gammas.recipeid.equals(id)){
                    MatchingGammas.add(Gammas.ingredient);
                }
            }
         
            for (LIDI Gammas : Gamma){
             for(String GammaSearch : MatchingGammas){ 
                  if(Gammas.ingredient.contains(GammaSearch) && !(RecipeIDgammas.contains(Gammas.recipeid)) ){
                            RecipesWithGamma.add(Gammas);
                            RecipeIDgammas.add(Gammas.recipeid);
                     }
                }
            } 
            
            /////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////
            
            
            
            for(LIDI Rhos : Rho){
                if(Rhos.recipeid.equals(id)){
                    MatchingRhos.add(Rhos.ingredient);
                }
            }
           
            for (LIDI Rhos : Rho){
             for(String RhoSearch : MatchingRhos){ 
                  if(Rhos.ingredient.contains(RhoSearch) && !(RecipeIDrhos.contains(Rhos.recipeid)) ){
                            RecipesWithRho.add(Rhos);
                            RecipeIDrhos.add(Rhos.recipeid);
                     }
                }
            } 

            /////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////
           
            
            List<LIDI> temp = new ArrayList<LIDI>();
           
           
            // getting ABRG
            List<LIDI> ABR1 = new ArrayList<LIDI>();
            List<LIDI> RG1 = new ArrayList<LIDI>();
            List<LIDI> ABRG = new ArrayList<LIDI>();
            ABR1 = findThree(RecipesWithAlpha,RecipesWithBeta,RecipesWithRho);
            RG1 = findTwo(RecipesWithRho,RecipesWithGamma);
            ABRG = findTwo(ABR1,RG1);
            temp = clearFound(RecipesWithAlpha,ABRG);
            RecipesWithAlpha = temp;
            temp = clearFound(RecipesWithBeta,ABRG);
            RecipesWithBeta = temp;
            temp = clearFound(RecipesWithRho,ABRG);
            RecipesWithRho = temp;
            temp = clearFound(RecipesWithGamma,ABRG);
            RecipesWithGamma = temp;
            //////////////////////////////////////////
            ////getting ABR
           List<LIDI> ABR = new ArrayList<LIDI>();
            ABR = findThree(RecipesWithAlpha,RecipesWithBeta,RecipesWithRho);
            temp = clearFound(RecipesWithAlpha,ABR);
            RecipesWithAlpha = temp;
            temp = clearFound(RecipesWithBeta,ABR);
            RecipesWithBeta = temp;
            temp = clearFound(RecipesWithRho,ABR);
            RecipesWithRho = temp; 
            //////////////////////////////////////////
            ////getting ABG
            List<LIDI> ABG = new ArrayList<LIDI>();
            ABG = findThree(RecipesWithAlpha,RecipesWithBeta,RecipesWithGamma);
            temp = clearFound(RecipesWithAlpha,ABG);
            RecipesWithAlpha = temp;
            temp = clearFound(RecipesWithBeta,ABG);
            RecipesWithBeta = temp;
            temp = clearFound(RecipesWithGamma,ABG);
            RecipesWithGamma = temp; 
            //////////////////////////////////////////
            ////getting ARG
            List<LIDI> ARG = new ArrayList<LIDI>();
            ARG = findThree(RecipesWithAlpha,RecipesWithRho,RecipesWithGamma);
            temp = clearFound(RecipesWithAlpha,ARG);
            RecipesWithAlpha = temp;
            temp = clearFound(RecipesWithRho,ARG);
            RecipesWithRho = temp;
            temp = clearFound(RecipesWithGamma,ARG);
            RecipesWithGamma = temp; 
            //////////////////////////////////////////
            ////getting BRG
            List<LIDI> BRG = new ArrayList<LIDI>();
            BRG = findThree(RecipesWithBeta,RecipesWithRho,RecipesWithGamma);
            temp = clearFound(RecipesWithBeta,BRG);
            RecipesWithBeta = temp;
            temp = clearFound(RecipesWithRho,BRG);
            RecipesWithRho = temp;
            temp = clearFound(RecipesWithGamma,BRG);
            RecipesWithGamma = temp; 
            //////////////////////////////////////////
            ////getting AB
            List<LIDI> AB = new ArrayList<LIDI>();
            AB = findTwo(RecipesWithAlpha,RecipesWithBeta);
            temp = clearFound(RecipesWithAlpha,AB);
            RecipesWithAlpha = temp;
            temp = clearFound(RecipesWithBeta,AB);
            RecipesWithBeta = temp;
            //////////////////////////////////////////
            ////getting RG
            List<LIDI> RG = new ArrayList<LIDI>();
            RG = findTwo(RecipesWithRho,RecipesWithGamma);
            temp = clearFound(RecipesWithRho,RG);
            RecipesWithRho = temp;
            temp = clearFound(RecipesWithGamma,RG);
            RecipesWithGamma = temp;
            //////////////////////////////////////////
            ////getting AR
            List<LIDI> AR = new ArrayList<LIDI>();
            AR = findTwo(RecipesWithAlpha,RecipesWithRho);
            temp = clearFound(RecipesWithAlpha,AR);
            RecipesWithAlpha = temp;
            temp = clearFound(RecipesWithRho,AR);
            RecipesWithRho = temp;
            //////////////////////////////////////////
            ////getting AG
            List<LIDI> AG = new ArrayList<LIDI>();
            AG = findTwo(RecipesWithAlpha,RecipesWithGamma);
            temp = clearFound(RecipesWithAlpha,AG);
            RecipesWithAlpha = temp;
            temp = clearFound(RecipesWithGamma,AG);
            RecipesWithGamma = temp;
            //////////////////////////////////////////
            ////getting BR
            List<LIDI> BR = new ArrayList<LIDI>();
            BR = findTwo(RecipesWithBeta,RecipesWithRho);
            temp = clearFound(RecipesWithBeta,BR);
            RecipesWithBeta = temp;
            temp = clearFound(RecipesWithRho,BR);
            RecipesWithRho = temp;
            //////////////////////////////////////////
            ////getting BG
            List<LIDI> BG = new ArrayList<LIDI>();
            BG = findTwo(RecipesWithBeta,RecipesWithGamma);
            temp = clearFound(RecipesWithBeta,BG);
            RecipesWithBeta = temp;
            temp = clearFound(RecipesWithGamma,BG);
            RecipesWithGamma = temp;
            ///////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////////////
            
      float TotalRecipes;
      TotalRecipes = ABRG.size() + ABR.size() + ABG.size() + ARG.size() + BRG.size() +  AB.size() + AR.size() + AG.size() + BR.size() + BG.size() + RG.size() + RecipesWithAlpha.size() + RecipesWithBeta.size() + RecipesWithRho.size() + RecipesWithGamma.size();
      
      float PMIofABRG;
      float PMIofABR;
      float PMIofABG;
      float PMIofARG;
      float PMIofBRG;
      float PMIofAB;
      float PMIofAR;
      float PMIofAG;
      float PMIofBR;
      float PMIofBG;
      float PMIofRG;
      
      float PMIofABRG1=0;
      float PMIofABR1=0;
      float PMIofABG1=0;
      float PMIofARG1=0;
      float PMIofBRG1=0;
      float PMIofAB1=0;
      float PMIofAR1=0;
      float PMIofAG1=0;
      float PMIofBR1=0;
      float PMIofBG1=0;
      float PMIofRG1=0;
      
      
      float filerecipes = numberofrecipes;
      
      PMIofABRG = (float) Math.log10((((TotalRecipes)*(ABRG.size()))/((RecipesWithAlpha.size())*(RecipesWithBeta.size())*(RecipesWithGamma.size())*(RecipesWithRho.size()))));     
      PMIofABR = (float) Math.log10((((TotalRecipes)*(ABR.size()))/((RecipesWithAlpha.size())*(RecipesWithBeta.size())*(RecipesWithRho.size()))));      
      PMIofABG = (float) Math.log10((((TotalRecipes)*(ABG.size()))/((RecipesWithAlpha.size())*(RecipesWithBeta.size())*(RecipesWithGamma.size()))));      
      PMIofARG = (float) Math.log10((((TotalRecipes)*(ARG.size()))/((RecipesWithAlpha.size())*(RecipesWithGamma.size())*(RecipesWithRho.size()))));      
      PMIofBRG = (float) Math.log10((((TotalRecipes)*(BRG.size()))/((RecipesWithBeta.size())*(RecipesWithGamma.size())*(RecipesWithRho.size()))));
      PMIofAB = (float)  Math.log10((((TotalRecipes)*(AB.size()))/((RecipesWithAlpha.size())*(RecipesWithBeta.size()))));
      PMIofAR = (float)  Math.log10((((TotalRecipes)*(AR.size()))/((RecipesWithAlpha.size())*(RecipesWithRho.size()))));   
      PMIofAG = (float)  Math.log10((((TotalRecipes)*(AG.size()))/((RecipesWithAlpha.size())*(RecipesWithGamma.size()))));     
      PMIofBR = (float)  Math.log10((((TotalRecipes)*(BR.size()))/((RecipesWithBeta.size())*(RecipesWithRho.size()))));      
      PMIofBG = (float)  Math.log10((((TotalRecipes)*(BG.size()))/((RecipesWithBeta.size())*(RecipesWithGamma.size()))));
      PMIofRG = (float)  Math.log10((((TotalRecipes)*(RG.size()))/((RecipesWithGamma.size())*(RecipesWithRho.size()))));
      
      PMIofABRG1 = (float) Math.log10((((filerecipes)*(ABRG.size()))/((RecipesWithAlpha.size())*(RecipesWithBeta.size())*(RecipesWithGamma.size())*(RecipesWithRho.size()))));     
      PMIofABR1 = (float) Math.log10((((filerecipes)*(ABR.size()))/((RecipesWithAlpha.size())*(RecipesWithBeta.size())*(RecipesWithRho.size()))));      
      PMIofABG1 = (float) Math.log10((((filerecipes)*(ABG.size()))/((RecipesWithAlpha.size())*(RecipesWithBeta.size())*(RecipesWithGamma.size()))));      
      PMIofARG1 = (float) Math.log10((((filerecipes)*(ARG.size()))/((RecipesWithAlpha.size())*(RecipesWithGamma.size())*(RecipesWithRho.size()))));      
      PMIofBRG1 = (float) Math.log10((((filerecipes)*(BRG.size()))/((RecipesWithBeta.size())*(RecipesWithGamma.size())*(RecipesWithRho.size()))));
      PMIofAB1 = (float)  Math.log10((((filerecipes)*(AB.size()))/((RecipesWithAlpha.size())*(RecipesWithBeta.size()))));
      PMIofAR1 = (float)  Math.log10((((filerecipes)*(AR.size()))/((RecipesWithAlpha.size())*(RecipesWithRho.size()))));   
      PMIofAG1 = (float)  Math.log10((((filerecipes)*(AG.size()))/((RecipesWithAlpha.size())*(RecipesWithGamma.size()))));     
      PMIofBR1 = (float)  Math.log10((((filerecipes)*(BR.size()))/((RecipesWithBeta.size())*(RecipesWithRho.size()))));      
      PMIofBG1 = (float)  Math.log10((((filerecipes)*(BG.size()))/((RecipesWithBeta.size())*(RecipesWithGamma.size()))));
      PMIofRG1 = (float)  Math.log10((((filerecipes)*(RG.size()))/((RecipesWithGamma.size())*(RecipesWithRho.size()))));
      
      
      
      if(PMIofABRG == Float.NEGATIVE_INFINITY || PMIofABRG == Float.POSITIVE_INFINITY || Float.isNaN(PMIofABRG) ){
          PMIofABRG = 0;
      }
      
      if(PMIofABR == Float.NEGATIVE_INFINITY || PMIofABR == Float.POSITIVE_INFINITY || Float.isNaN(PMIofABR)){
          PMIofABR = 0;
      }
      if(PMIofABG == Float.NEGATIVE_INFINITY || PMIofABG == Float.POSITIVE_INFINITY || Float.isNaN(PMIofABG) ){
          PMIofABG = 0;
      }
      if(PMIofARG == Float.NEGATIVE_INFINITY || PMIofARG == Float.POSITIVE_INFINITY || Float.isNaN(PMIofARG)){
          PMIofARG = 0;
      }
      if(PMIofBRG == Float.NEGATIVE_INFINITY || PMIofBRG == Float.POSITIVE_INFINITY || Float.isNaN(PMIofBRG) ){
          PMIofBRG = 0;
      }
      if(PMIofAB == Float.NEGATIVE_INFINITY || PMIofAB == Float.POSITIVE_INFINITY || Float.isNaN(PMIofAB) ){
          PMIofAB = 0;
      }
      if(PMIofAR == Float.NEGATIVE_INFINITY || PMIofAR == Float.POSITIVE_INFINITY || Float.isNaN(PMIofAR)  ){
          PMIofAR = 0;
      }
      if(PMIofAG == Float.NEGATIVE_INFINITY || PMIofAG == Float.POSITIVE_INFINITY || Float.isNaN(PMIofAG) ){
          PMIofAG = 0;
      }
      if(PMIofBR == Float.NEGATIVE_INFINITY || PMIofBR == Float.POSITIVE_INFINITY || Float.isNaN(PMIofBR) ){
          PMIofBR = 0;
      }
      if(PMIofBG == Float.NEGATIVE_INFINITY || PMIofBG == Float.POSITIVE_INFINITY || Float.isNaN(PMIofBG) ){
          PMIofBG = 0;
      }
      if(PMIofRG == Float.NEGATIVE_INFINITY || PMIofRG == Float.POSITIVE_INFINITY || Float.isNaN(PMIofRG) ){
          PMIofRG = 0;
      }
          
      if( PMIofABRG1 == Float.NEGATIVE_INFINITY || PMIofABRG1 == Float.POSITIVE_INFINITY || Float.isNaN(PMIofABRG1)){
          PMIofABRG1 = 0;
      }
      if( PMIofABR1 == Float.NEGATIVE_INFINITY  || PMIofABR1 == Float.POSITIVE_INFINITY || Float.isNaN(PMIofABR1)){
          PMIofABR1 = 0;
      }
      if( PMIofABG1 == Float.NEGATIVE_INFINITY  || PMIofABG1 == Float.POSITIVE_INFINITY || Float.isNaN(PMIofABG1)){
          PMIofABG1 = 0;
      }
      if( PMIofARG1 == Float.NEGATIVE_INFINITY  || PMIofARG1 == Float.POSITIVE_INFINITY || Float.isNaN(PMIofARG1)){  
          PMIofARG1 = 0;
      }  
      if( PMIofBRG1 == Float.NEGATIVE_INFINITY  || PMIofBRG1 == Float.POSITIVE_INFINITY || Float.isNaN(PMIofBRG1)){ 
          PMIofBRG1 = 0;
      }   
      if( PMIofAB1 == Float.NEGATIVE_INFINITY || PMIofAB1 == Float.POSITIVE_INFINITY || Float.isNaN(PMIofAB1)){    
          PMIofAB1 = 0;
      }
      if( PMIofAR1 == Float.NEGATIVE_INFINITY || PMIofAR1 == Float.POSITIVE_INFINITY || Float.isNaN(PMIofAR1)){ 
          PMIofAR1 = 0;
      }   
      if( PMIofAG1 == Float.NEGATIVE_INFINITY || PMIofAG1 == Float.POSITIVE_INFINITY || Float.isNaN(PMIofAG1)){
          PMIofAG1 = 0;
      }
      if( PMIofBR1 == Float.NEGATIVE_INFINITY || PMIofBR1 == Float.POSITIVE_INFINITY || Float.isNaN(PMIofBR1)){ 
          PMIofBR1 = 0;
      }   
      if( PMIofBG1 == Float.NEGATIVE_INFINITY || PMIofBG1 == Float.POSITIVE_INFINITY || Float.isNaN(PMIofBG1)){ 
          PMIofBG1 = 0;
      }   
      if( PMIofRG1 == Float.NEGATIVE_INFINITY || PMIofRG1 == Float.POSITIVE_INFINITY || Float.isNaN(PMIofRG1)){ 
          PMIofRG1 = 0;
      }   
          
      
              
      float SumPMI;
      float SumPMI1;
      
      
      SumPMI = PMIofABRG + PMIofABR + PMIofABG + PMIofARG + PMIofBRG + PMIofAB + PMIofAR + PMIofAG + PMIofBR + PMIofBG + PMIofRG;
      
      SumPMI1 = PMIofABRG1 + PMIofABR1 + PMIofABG1 + PMIofARG1 + PMIofBRG1 + PMIofAB1 + PMIofAR1 + PMIofAG1 + PMIofBR1 + PMIofBG1 + PMIofRG1;
           
     if(SumPMI == 0){
         SumPMI = 1;
     }
     
     if(SumPMI1 == 0){
         SumPMI1 = 1;
     }
      

      float PredictedRating;
      
      PredictedRating = (Float.valueOf(countAndAverage(ABRG)) * ((PMIofABRG)/SumPMI)) +
                        (Float.valueOf(countAndAverage(ABR)) * ((PMIofABR)/SumPMI)) +
                        (Float.valueOf(countAndAverage(ABG)) * ((PMIofABG)/SumPMI)) +
                        (Float.valueOf(countAndAverage(ARG)) * ((PMIofARG)/SumPMI))+
                        (Float.valueOf(countAndAverage(BRG)) * ((PMIofBRG)/SumPMI))+
                        (Float.valueOf(countAndAverage(AB)) * ((PMIofAB)/SumPMI))+
                        (Float.valueOf(countAndAverage(AR)) * ((PMIofAR)/SumPMI))+
                        (Float.valueOf(countAndAverage(AG)) * ((PMIofAG)/SumPMI))+
                        (Float.valueOf(countAndAverage(BR)) * ((PMIofBR)/SumPMI))+
                        (Float.valueOf(countAndAverage(BG)) * ((PMIofBG)/SumPMI))+
                        (Float.valueOf(countAndAverage(RG)) * ((PMIofRG)/SumPMI));
      
      float PredictedRating1;
      
      PredictedRating1 = (Float.valueOf(countAndAverage(ABRG)) * ((PMIofABRG1)/SumPMI1)) +
                        (Float.valueOf(countAndAverage(ABR)) * ((PMIofABR1)/SumPMI1)) +
                        (Float.valueOf(countAndAverage(ABG)) * ((PMIofABG1)/SumPMI1)) +
                        (Float.valueOf(countAndAverage(ARG)) * ((PMIofARG1)/SumPMI1))+
                        (Float.valueOf(countAndAverage(BRG)) * ((PMIofBRG1)/SumPMI1))+
                        (Float.valueOf(countAndAverage(AB)) * ((PMIofAB1)/SumPMI1))+
                        (Float.valueOf(countAndAverage(AR)) * ((PMIofAR1)/SumPMI1))+
                        (Float.valueOf(countAndAverage(AG)) * ((PMIofAG1)/SumPMI1))+
                        (Float.valueOf(countAndAverage(BR)) * ((PMIofBR1)/SumPMI1))+
                        (Float.valueOf(countAndAverage(BG)) * ((PMIofBG1)/SumPMI1))+
                        (Float.valueOf(countAndAverage(RG)) * ((PMIofRG1)/SumPMI1));
      
      
      Label recipeidnumber = new Label(0, lineNUM, "recipe id " + id);
      sheet0.addCell(recipeidnumber);
      lineNUM++;
      lineNUM++;
      
      Label recipeABRG = new Label(0, lineNUM, "ABRG");
      sheet0.addCell(recipeABRG);
      Label recipeABRG1 = new Label(1, lineNUM, String.valueOf(ABRG.size()));
      sheet0.addCell(recipeABRG1);
      Label recipeABRG2 = new Label(2, lineNUM, countAndAverage(ABRG));
      sheet0.addCell(recipeABRG2);
      Label recipeABRG3 = new Label(3, lineNUM, String.valueOf(PMIofABRG));
      sheet0.addCell(recipeABRG3);
      Label recipeABRG4 = new Label(4, lineNUM, String.valueOf(PMIofABRG1));
      sheet0.addCell(recipeABRG4);
      lineNUM++;
      
      
      Label recipeABR = new Label(0, lineNUM, "ABR");
      sheet0.addCell(recipeABR);
      Label recipeABR1 = new Label(1, lineNUM, String.valueOf(ABR.size()));
      sheet0.addCell(recipeABR1);
      Label recipeABR2 = new Label(2, lineNUM, countAndAverage(ABR));
      sheet0.addCell(recipeABR2);
      Label recipeABR3 = new Label(3, lineNUM, String.valueOf(PMIofABR));
      sheet0.addCell(recipeABR3);
      Label recipeABR4 = new Label(4, lineNUM, String.valueOf(PMIofABR1));
      sheet0.addCell(recipeABR4);
      lineNUM++;
      
      Label recipeABG = new Label(0, lineNUM, "ABG");
      sheet0.addCell(recipeABG);
      Label recipeABG1 = new Label(1, lineNUM, String.valueOf(ABG.size()));
      sheet0.addCell(recipeABG1);
      Label recipeABG2 = new Label(2, lineNUM, countAndAverage(ABG));
      sheet0.addCell(recipeABG2);
      Label recipeABG3 = new Label(3, lineNUM, String.valueOf(PMIofABG));
      sheet0.addCell(recipeABG3);
      Label recipeABG4 = new Label(4, lineNUM, String.valueOf(PMIofABG1));
      sheet0.addCell(recipeABG4);
      lineNUM++;
      
      
      Label recipeARG = new Label(0, lineNUM, "ARG");
      sheet0.addCell(recipeARG);
      Label recipeARG1 = new Label(1, lineNUM, String.valueOf(ARG.size()));
      sheet0.addCell(recipeARG1);
      Label recipeARG2 = new Label(2, lineNUM, countAndAverage(ARG));
      sheet0.addCell(recipeARG2);
      Label recipeARG3 = new Label(3, lineNUM, String.valueOf(PMIofARG));
      sheet0.addCell(recipeARG3);
      Label recipeARG4 = new Label(4, lineNUM, String.valueOf(PMIofARG1));
      sheet0.addCell(recipeARG4);
      lineNUM++;
      
      Label recipeBRG = new Label(0, lineNUM, "BRG");
      sheet0.addCell(recipeBRG);
      Label recipeBRG1 = new Label(1, lineNUM, String.valueOf(BRG.size()));
      sheet0.addCell(recipeBRG1);
      Label recipeBRG2 = new Label(2, lineNUM, countAndAverage(BRG));
      sheet0.addCell(recipeBRG2);
      Label recipeBRG3 = new Label(3, lineNUM, String.valueOf(PMIofBRG));
      sheet0.addCell(recipeBRG3);
      Label recipeBRG4 = new Label(4, lineNUM, String.valueOf(PMIofBRG1));
      sheet0.addCell(recipeBRG4);
      lineNUM++;
      
      Label recipeAB = new Label(0, lineNUM, "AB");
      sheet0.addCell(recipeAB);
      Label recipeAB1 = new Label(1, lineNUM, String.valueOf(AB.size()));
      sheet0.addCell(recipeAB1);
      Label recipeAB2 = new Label(2, lineNUM, countAndAverage(AB));
      sheet0.addCell(recipeAB2);
      Label recipeAB3 = new Label(3, lineNUM, String.valueOf(PMIofAB));
      sheet0.addCell(recipeAB3);
      Label recipeAB4 = new Label(4, lineNUM, String.valueOf(PMIofAB1));
      sheet0.addCell(recipeAB4);
      lineNUM++;
      
      Label recipeAR = new Label(0, lineNUM, "AR");
      sheet0.addCell(recipeAR);
      Label recipeAR1 = new Label(1, lineNUM, String.valueOf(AR.size()));
      sheet0.addCell(recipeAR1);
      Label recipeAR2 = new Label(2, lineNUM, countAndAverage(AR));
      sheet0.addCell(recipeAR2);
      Label recipeAR3 = new Label(3, lineNUM, String.valueOf(PMIofAR));
      sheet0.addCell(recipeAR3);
      Label recipeAR4 = new Label(4, lineNUM, String.valueOf(PMIofAR1));
      sheet0.addCell(recipeAR4);
      lineNUM++;
      
      Label recipeAG = new Label(0, lineNUM, "AG");
      sheet0.addCell(recipeAG);
      Label recipeAG1 = new Label(1, lineNUM, String.valueOf(AG.size()));
      sheet0.addCell(recipeAG1);
      Label recipeAG2 = new Label(2, lineNUM, countAndAverage(AG));
      sheet0.addCell(recipeAG2);
      Label recipeAG3 = new Label(3, lineNUM, String.valueOf(PMIofAG));
      sheet0.addCell(recipeAG3);
      Label recipeAG4 = new Label(4, lineNUM, String.valueOf(PMIofAG1));
      sheet0.addCell(recipeAG4);
      lineNUM++;
      
      Label recipeBR = new Label(0, lineNUM, "BR");
      sheet0.addCell(recipeBR);
      Label recipeBR1 = new Label(1, lineNUM, String.valueOf(BR.size()));
      sheet0.addCell(recipeBR1);
      Label recipeBR2 = new Label(2, lineNUM, countAndAverage(BR));
      sheet0.addCell(recipeBR2);
      Label recipeBR3 = new Label(3, lineNUM, String.valueOf(PMIofBR));
      sheet0.addCell(recipeBR3);
      Label recipeBR4 = new Label(4, lineNUM, String.valueOf(PMIofBR1));
      sheet0.addCell(recipeBR4);
      lineNUM++;
      
      Label recipeBG = new Label(0, lineNUM, "BG");
      sheet0.addCell(recipeBG);
      Label recipeBG1 = new Label(1, lineNUM, String.valueOf(BG.size()));
      sheet0.addCell(recipeBG1);
      Label recipeBG2 = new Label(2, lineNUM, countAndAverage(BG));
      sheet0.addCell(recipeBG2);
      Label recipeBG3 = new Label(3, lineNUM, String.valueOf(PMIofBG));
      sheet0.addCell(recipeBG3);
      Label recipeBG4 = new Label(4, lineNUM, String.valueOf(PMIofBG1));
      sheet0.addCell(recipeBG4);
      lineNUM++;
      
      Label recipeRG = new Label(0, lineNUM, "RG");
      sheet0.addCell(recipeRG);
      Label recipeRG1 = new Label(1, lineNUM, String.valueOf(RG.size()));
      sheet0.addCell(recipeRG1);
      Label recipeRG2 = new Label(2, lineNUM, countAndAverage(RG));
      sheet0.addCell(recipeRG2);
      Label recipeRG3 = new Label(3, lineNUM, String.valueOf(PMIofRG));
      sheet0.addCell(recipeRG3);
      Label recipeRG4 = new Label(4, lineNUM, String.valueOf(PMIofRG1));
      sheet0.addCell(recipeRG4);
      lineNUM++;
      
      Label recipeA = new Label(0, lineNUM, "A");
      sheet0.addCell(recipeA);
      Label recipeA1 = new Label(1, lineNUM, String.valueOf(RecipesWithAlpha.size()));
      sheet0.addCell(recipeA1);
      Label recipeA2 = new Label(2, lineNUM, countAndAverage(RecipesWithAlpha));
      sheet0.addCell(recipeA2);
      lineNUM++;
      
      Label recipeB = new Label(0, lineNUM, "B");
      sheet0.addCell(recipeB);
      Label recipeB1 = new Label(1, lineNUM, String.valueOf(RecipesWithBeta.size()));
      sheet0.addCell(recipeB1);
      Label recipeB2 = new Label(2, lineNUM, countAndAverage(RecipesWithBeta));
      sheet0.addCell(recipeB2);
      lineNUM++;
      
      Label recipeR = new Label(0, lineNUM, "R");
      sheet0.addCell(recipeR);
      Label recipeR1 = new Label(1, lineNUM, String.valueOf(RecipesWithRho.size()));
      sheet0.addCell(recipeR1);
      Label recipeR2 = new Label(2, lineNUM, countAndAverage(RecipesWithRho));
      sheet0.addCell(recipeR2);
      lineNUM++;
      
      Label recipeG = new Label(0, lineNUM, "G");
      sheet0.addCell(recipeG);
      Label recipeG1 = new Label(1, lineNUM, String.valueOf(RecipesWithGamma.size()));
      sheet0.addCell(recipeG1);
      Label recipeG2 = new Label(2, lineNUM, countAndAverage(RecipesWithGamma));
      sheet0.addCell(recipeG2);
      lineNUM++;
      
      lineNUM = lineNUM + 2;
      String actualrating = "";
      if(!Alpha.isEmpty()){
          actualrating = Alpha.get(1).rating;
      }
      if(!Beta.isEmpty()){
          actualrating = Beta.get(1).rating;
      }
      if(!Gamma.isEmpty()){
          actualrating = Gamma.get(1).rating;
      }
      if(!Rho.isEmpty()){
          actualrating = Rho.get(1).rating;
      }
    

      Label PR = new Label(6, lineNUM,"Predicted Rating with recipes with at least one match as total" );
      sheet0.addCell(PR);
      Label PR1 = new Label(7, lineNUM,String.valueOf(PredictedRating));
      sheet0.addCell(PR1);
      lineNUM++;
      Label PR2 = new Label(6, lineNUM,"Predicted Rating Total recipes used" );
      sheet0.addCell(PR2);
      Label PR3 = new Label(7, lineNUM,String.valueOf(PredictedRating1));
      sheet0.addCell(PR3);
      lineNUM++;
      Label ActualRating1 = new Label(6, lineNUM,"Actual Rating" );
      sheet0.addCell(ActualRating1);
      Label ActualRating2 = new Label(7, lineNUM, actualrating);
      sheet0.addCell(ActualRating2);
      
      lineNUM = lineNUM + 4;
  
              
        }
        
        workbook.write(); 
        workbook.close();
            
            
    }
    
     public static class LIDI {
            public String ingredient;
            public String recipe;
            public String recipeid;
            public String rating;
            
            //constructor
            public void setINGREDIENT(String ingredient1) {
                this.ingredient = ingredient1;
            }
            
            public String getIngredient (){
                return this.ingredient;
            }
            
            public void setRecipe (String recipe){
                this.recipe = recipe;
            }
            
            public String getRecipe (){
                return this.recipe;
            }
            
            public void setRecipeID (String recipeid){
                this.recipeid = recipeid;
            }
            
            public String getRecipeID (){
                return this.recipeid;
            }
            
            public void setRating (String rating){
                this.rating = rating;
            }
            
            public String getRating(){
                return this.rating;
            }
            
        }
            
            public static String countAndAverage (List<LIDI> XXX){
                if(XXX.isEmpty()){
                    return "0";
                }
                
                float addedRatings = 0;
                for(LIDI x : XXX){
                   addedRatings = addedRatings + Float.valueOf(x.rating); 
                }
                
                float averageRating;
                averageRating = (addedRatings/XXX.size());
                
                return String.valueOf(averageRating);
                
            }
            
            public static List<LIDI> clearFound (List<LIDI> ABRG, List<LIDI> FOUNDids){
                boolean notfound = true;
                List<LIDI> tmpList = new ArrayList<LIDI>();
                for(LIDI recp : ABRG){ 
                for(LIDI ab : FOUNDids){
                        if(recp.recipeid.equals(ab.recipeid)){
                            notfound = false;
                        }
                    }
                if(notfound){
                    tmpList.add(recp);
                }
                notfound = true;
            }
            return tmpList;
            
            }
            
            public static List<LIDI> findThree (List<LIDI> ONE, List<LIDI> TWO, List<LIDI> THREE){
            List<LIDI> XXX = new ArrayList<LIDI>();
            
            for(LIDI alphalist : ONE){
                for(LIDI betalist : TWO){
                        for (LIDI rholist : THREE){
                            if(rholist.recipeid.equals(betalist.recipeid) && betalist.recipeid.equals(alphalist.recipeid)){
                                XXX.add(rholist);
                            }
                         }
                    }
            }
            
            return XXX;
                
            }
            
            public static List<LIDI> findTwo (List<LIDI> ONE, List<LIDI> TWO){
            List<LIDI> XX = new ArrayList<LIDI>();
            
            for(LIDI searchA : ONE){
                for(LIDI searchB : TWO){
                            if(searchA.recipeid.equals(searchB.recipeid)){
                                XX.add(searchA);
                            }
                }
            }
            
            return XX;
               
            }
            
            public static void check (List<LIDI> MAIN, List<LIDI> alreadyFound){
                 for(LIDI rec : MAIN){
                for(LIDI ab : alreadyFound){
                    if(rec.recipeid.equals(ab.recipeid)){
                            System.out.println("notyet");
                        }
                }
            }
            }
    
}
