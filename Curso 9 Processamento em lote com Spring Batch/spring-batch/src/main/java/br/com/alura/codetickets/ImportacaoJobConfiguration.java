package br.com.alura.codetickets;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Configuration
public class ImportacaoJobConfiguration {

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * cria o job responsável pela execução de leitura do arquivo csv
     *
     * @param passoInicial
     * @param jobRepository
     * @return
     */
    @Bean
    public Job job(Step passoInicial, JobRepository jobRepository) {
        return new JobBuilder("geracao-tickets", jobRepository)
                .start(passoInicial)
                .incrementer(new RunIdIncrementer()) //incrementa a cada execucao desse job
                .build();
    }

    /**
     * Cria o passo inicial
     * <Importacao, Importacao> indica qual dado esta lendo e qual esta escrevendo nesse caso sao iguais
     * chunk(200, transactionManager) indica qtos dados serao processados por bloco de transacao, faz o envio/roolback de 200 em 200
     *
     * @param reader
     * @param writer
     * @param jobRepository
     * @return
     */
    @Bean
    public Step passoInicial(ItemReader<Importacao> reader, ItemWriter<Importacao> writer, JobRepository jobRepository) {
        return new StepBuilder("passo-inicial", jobRepository)
                .<Importacao, Importacao>chunk(200, transactionManager)
                .reader(reader) //faz a leitura
                .processor(processor()) //chama o processor para fazer o processamento de dados nesse caso adicionar o valor de taxa
                .writer(writer) //faz a escrita
                .build();
    }

    /**
     * Faz a leitura do arquivo csv dentro da pasta files conforme atributos da classe importacao
     * A configuração do FlatFileItemReader (tipo para csv) deve especificar o caractere delimitador do arquivo CSV,
     * via chamada ao metodo .delimiter(";"). Caso contrário, a leitura dos dados será incorreta.
     *
     * @return
     */
    @Bean
    public ItemReader<Importacao> reader() {
        return new FlatFileItemReaderBuilder<Importacao>()
                .name("leitura-csv")
                .resource(new FileSystemResource("files/dados.csv"))
                .comments("--") //faz com q se quiser passar um comentario toda linha q inicia com -- é uma linha de comentario
                .delimited()
                .delimiter(";") //informa o tipo de limitador o arquivo usa nesse caso eh ;
                .names("cpf", "cliente", "nascimento", "evento", "data", "tipoIngresso", "valor")
                //.targetType(Importacao.class) //informa o tipo da classe
                .fieldSetMapper(new ImportacaoMapper()) //agora fazemos o mapeamento dos campos por essa classe mapper
                .build();
    }

    /**
     * metodo que escreve os dados no banco esse metodo jdbcBatch nao usa o jpa
     *
     * @param dataSource
     * @return
     */
    @Bean
    public ItemWriter<Importacao> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Importacao>()
                .dataSource(dataSource)
                .sql(
                        "INSERT INTO importacao (cpf, cliente, nascimento, evento, data, tipo_ingresso, valor, hora_importacao, taxa_adm) VALUES" +
                                " ( :cpf, :cliente, :nascimento, :evento, :data, :tipoIngresso, :valor, :horaImportacao, :taxaAdm )"
                )
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>()) //indica da onde esta vindo os parametros que e do ben de leitura do arquivo
                .build();
    }

    /**
     * Item processor para fazer a execução de processos durante a execução do step
     * @return
     */
    @Bean
    public ImportacaoProcessor processor() {
        return new ImportacaoProcessor();
    }

}
