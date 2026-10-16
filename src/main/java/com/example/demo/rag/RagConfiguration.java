package com.example.demo.rag;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Configuration
public class RagConfiguration {
    private final Logger log = LoggerFactory.getLogger(RagConfiguration.class);

    private String vectorStoreName = "vectorstore.json";

    @Value("classpath:/data/models.json")
    private Resource models;

    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel){
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        var vecStoreFile = getVectorStoreFile();
        if(vecStoreFile.exists()){
            log.info("Vector Store already exists");
            simpleVectorStore.load(vecStoreFile);
        }else{
            log.info("Creating Vector Store");
            TextReader textReader = new TextReader(models);
            textReader.getCustomMetadata().put("fileName", "models.txt");
            List<Document> documents = textReader.get();
            TokenTextSplitter.Builder builder = TokenTextSplitter.builder();
            List<Document> splitDocuments = builder.build().apply(documents);

            simpleVectorStore.add(splitDocuments);
            simpleVectorStore.save(vecStoreFile);
        }
        return simpleVectorStore;
    }

    private File getVectorStoreFile(){
        Path path = Paths.get("src", "main", "resources", "data");
        String absolutePath = path.toFile().getAbsolutePath()+ "/" +vectorStoreName;
        return new File(absolutePath);
    }
}
