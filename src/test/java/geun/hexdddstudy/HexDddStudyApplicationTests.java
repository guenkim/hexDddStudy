package geun.hexdddstudy;

import geun.HexDddStudyApplication;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;


class HexDddStudyApplicationTests {

    @Test
    void run() {
        try(MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            HexDddStudyApplication.main(new String[0]);

            mocked.verify(() -> SpringApplication.run(HexDddStudyApplication.class, new String[0]));
        }
    }

}
