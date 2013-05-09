package xml_handler;

import XMLMainFile.AddingRating;
import excelread.Excelread.INGRED;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import xmlingredientnormalizer.ImportedRecipe;
import xmlingredientnormalizer.Ingredient;
import xmlingredientnormalizer.RecipeWrapper;
import xmlingredientnormalizer.NewImportedRecipe;
import xmlingredientnormalizer.NewIngredient;
import xmlingredientnormalizer.NewRecipeWrapper;
import xmlingredientnormalizer.SemanticNormalization;
import xmlingredientnormalizer.FindRho;
import xmlingredientnormalizer.LIDIAlgo.LIDI;

public class JAXBXMLHandler {

    
    public JAXBXMLHandler() {
    }

    // Import

    
    public void unmarshalremarshal(File importFile, File exportFile) throws IOException, JAXBException, FileNotFoundException, WriteException, BiffException {
        NewImportedRecipe tmpRecipe = null;
        NewRecipeWrapper NewRecWrapper = new NewRecipeWrapper();
        NewIngredient tIngredient;
        JAXBContext jc = JAXBContext.newInstance(RecipeWrapper.class);
        RecipeWrapper storageWrapper = new RecipeWrapper();
        Unmarshaller u = jc.createUnmarshaller();
        RecipeWrapper tmpWrapper = null;
        
        List <INGRED> ALPHASEARCH = excelread.Excelread.main();
        List <INGRED> BETASEARCH = excelread.Excelread.getBeta();
                
        try {
            tmpWrapper = (RecipeWrapper) u.unmarshal(importFile);
        } catch (Exception fileNotFound) {
            System.err.print("Error: " + fileNotFound.getMessage());            
        }        

        
        for (ImportedRecipe element : tmpWrapper.getList()) {
            tmpRecipe = new NewImportedRecipe();
            
            tmpRecipe.setCheckFlag(element.isCheckFlag());
            tmpRecipe.setCreateDate(element.getCreateDate());
            tmpRecipe.setDirections(element.getDirections());
            tmpRecipe.setErrorString(element.getErrorString());
            tmpRecipe.setIngredientStrings(element.getIngredientStrings());
            tmpRecipe.setParserVersion(element.getParserVersion());
            tmpRecipe.setServings(element.getServings());
            tmpRecipe.setSourceURL(element.getSourceURL());
            tmpRecipe.setSubtitles(element.isSubtitles());
            tmpRecipe.setTimes(element.getTimes());
            tmpRecipe.setUserRating(element.GetUserRating());
            tmpRecipe.setTitle(element.getTitle());

            for (Ingredient tmpIngredient : element.getIngredients()) {
                tIngredient = new NewIngredient();
                tIngredient.setAddedInformation(tmpIngredient.getAddedInformation());
                tIngredient.setAmount(tmpIngredient.getAmount());
                tIngredient.setDescription(tmpIngredient.getDescription());
                tIngredient.setFullIngredientString(tmpIngredient.getFullIngredientString());
                tIngredient.setStatus(tmpIngredient.getStatus());
                tIngredient.setUom(tmpIngredient.getUom());
                tIngredient.setIdentifier(SemanticNormalization.main(tmpIngredient.getAmount(), tmpIngredient.getUom(), tmpIngredient.getDescription(), element.getTitle()));

                tmpRecipe.getIngredients().add(tIngredient);
            }            
            
            tmpRecipe.setRho(FindRho.main(tmpRecipe.getDirections()));
            tmpRecipe.setAlpha(FindRho.GetAlpha(tmpRecipe.getIngredients(), ALPHASEARCH));
            tmpRecipe.setBeta(FindRho.GetBeta(tmpRecipe.getIngredients(), BETASEARCH));
            tmpRecipe.setGamma(FindRho.GetGamma(tmpRecipe.getIngredients()));
            
            NewRecWrapper.getList().add(tmpRecipe);
        }
        
        

        int recipeid = 0;
        List <LIDI> Alpha = new ArrayList<LIDI>();
        List <LIDI> Beta = new ArrayList<LIDI>();
        List <LIDI> Rho = new ArrayList<LIDI>();
        List <LIDI> Gamma = new ArrayList<LIDI>();
        
        
        for(NewImportedRecipe recipe : NewRecWrapper.getList()){
            
            for(String alpha : recipe.getAlpha()){
                
                LIDI tmp = new LIDI(); 
                tmp.setINGREDIENT(alpha);
                tmp.setRating(String.valueOf(recipe.GetUserRating()));
                tmp.setRecipe(recipe.getTitle());
                tmp.setRecipeID(String.valueOf(recipeid));
                Alpha.add(tmp);
                
             }
            
            for(String beta : recipe.getBeta()){
            
                LIDI tmp = new LIDI(); 
                tmp.setINGREDIENT(beta);
                tmp.setRating(String.valueOf(recipe.GetUserRating()));
                tmp.setRecipe(recipe.getTitle());
                tmp.setRecipeID(String.valueOf(recipeid));
                Beta.add(tmp);
             }
            
            for(String rho : recipe.getRho()){
            
                LIDI tmp = new LIDI(); 
                tmp.setINGREDIENT(rho);
                tmp.setRating(String.valueOf(recipe.GetUserRating()));
                tmp.setRecipe(recipe.getTitle());
                tmp.setRecipeID(String.valueOf(recipeid));
                Rho.add(tmp);
             }
            
            for(String gamma : recipe.getGamma()){
            
                LIDI tmp = new LIDI(); 
                tmp.setINGREDIENT(gamma);
                tmp.setRating(String.valueOf(recipe.GetUserRating()));
                tmp.setRecipe(recipe.getTitle());
                tmp.setRecipeID(String.valueOf(recipeid));
                Gamma.add(tmp);
             }
            
            recipeid++;
            
            
        }
        
        xmlingredientnormalizer.LIDIAlgo.main(Alpha, Beta, Rho, Gamma,recipeid);

        NewRecWrapper.getUnstoredRecipeList().addAll(tmpWrapper.getUnstoredRecipeList());

        JAXBContext njc = JAXBContext.newInstance(NewRecipeWrapper.class);
        Marshaller m = njc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(NewRecWrapper, new FileOutputStream(exportFile));
    }
    private static final Logger LOG = Logger.getLogger(JAXBXMLHandler.class.getName());
}
