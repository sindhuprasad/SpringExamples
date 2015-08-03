package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class ScoreItemProcessor implements ItemProcessor<Score, Grade> {

    private static final Logger log = LoggerFactory.getLogger(ScoreItemProcessor.class);

    @Override
    public Grade process(final Score studentScore) throws Exception {

        String grade=null;
        if(studentScore.getScore() >= 90)
            grade = "A";
        if(80 <= studentScore.getScore() && studentScore.getScore() <= 89)
            grade = "B";
        if(70 <= studentScore.getScore() && studentScore.getScore() <= 79)
            grade = "C";
        if(60 <= studentScore.getScore() && studentScore.getScore() <= 69)
            grade = "D";
        if(studentScore.getScore() < 60)
            grade = "F";

        Grade transformedGrade = new Grade(studentScore.getStudentID(), studentScore.getCourseID(), studentScore.getScore(), grade);

        log.info("Converting (" + studentScore.getScore() + ") into (" + grade + ")");

        return transformedGrade;
    }

}
