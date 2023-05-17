package sk.stuba.fei.uim.vsa.pr2.bonus;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sk.stuba.fei.uim.vsa.pr2.ResourceTest;
import sk.stuba.fei.uim.vsa.pr2.TestData;
import sk.stuba.fei.uim.vsa.pr2.model.dto.response.ThesisResponse;
import sk.stuba.fei.uim.vsa.pr2.model.dto.response.teacher.TeacherWithThesesResponse;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static sk.stuba.fei.uim.vsa.pr2.TeacherResourceTest.createTeacher;
import static sk.stuba.fei.uim.vsa.pr2.TestData.ARRAY_CONTENT_LENGTH;
import static sk.stuba.fei.uim.vsa.pr2.TestData.OBJECT_CONTENT_LENGTH;
import static sk.stuba.fei.uim.vsa.pr2.ThesisResourceTest.createThesis;

@Slf4j
public class BonusSearchResourceTest extends ResourceTest {

    public static final String SEARCH_PATH = "/search";
    public static final String SEARCH_THESES_PATH = "/search/theses";
    public static final String SEARCH_TEACHER_PATH = "/search/teachers";
    public static final String SEARCH_STUDENT_PATH = "/search/students";

    private static final Map<String, Object> pageParams = new HashMap<>();

    @Test
    public void shouldFindThesesWithPageOf5() {
        Long t01Id = Objects.requireNonNull(createTeacher(TestData.T01)).readEntity(TeacherWithThesesResponse.class).getId();
        Set<Long> thIds = new HashSet<>();
        final int numOfThesis = 4;
        for (int i = 0; i < numOfThesis; i++) {
            try (Response newThesis = createThesis(new TestData.Thesis(
                    "FEI-666" + i,
                    TestData.TH01.title,
                    TestData.TH01.description,
                    i % 2 == 0 ? ThesisResponse.Type.BACHELOR.toString() : ThesisResponse.Type.MASTER.toString()
            ), TestData.T01, false)) {
                assertNotNull(newThesis);
                assertTrue(newThesis.hasEntity());
                ThesisResponse body = newThesis.readEntity(ThesisResponse.class);
                assertNotNull(body);
                assertNotNull(body.getId());
                thIds.add(body.getId());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                fail(e);
            }
        }
        assertEquals(numOfThesis, thIds.size());

        pageParams.put("page", 0);
        pageParams.put("size", 3);
        try (Response response = request(SEARCH_THESES_PATH, TestData.T01, pageParams)
                .post(Entity.entity(
                        SearchThesesRequest.builder()
                                .teacherId(t01Id)
                                .type(ThesisResponse.Type.BACHELOR)
                                .status(ThesisResponse.Status.FREE_TO_TAKE)
                                .build(),
                        MediaType.APPLICATION_JSON))) {
            assertNotNull(response);
            assertTrue(response.hasEntity());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertTrue(response.getLength() > ARRAY_CONTENT_LENGTH);
            assertTrue(response.hasEntity());
            assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
            PagedResponse<ThesisResponse> page = response.readEntity(new GenericType<PagedResponse<ThesisResponse>>() {
            });
            assertNotNull(page);
            assertNotNull(page.getContent());
            assertNotNull(page.getPage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e);
        }
    }

    @Test
    @Disabled
    public void shouldFindTeachers() {
        Long t01Id = Objects.requireNonNull(createTeacher(TestData.T01)).readEntity(TeacherWithThesesResponse.class).getId();
        final int numTeachers = 6;
        Set<Long> tIds = new HashSet<>();
        for (int i = 0; i < numTeachers; i++) {
            try (Response newTeacher = createTeacher(new TestData.Teacher(
                    Integer.valueOf(i + 1).longValue(),
                    TestData.T01.name + i,
                    "teacher" + i + "@stuba.sk",
                    i % 2 == 0 ? TestData.T01.institute + " generated" : TestData.T02.institute,
                    TestData.T01.department + "generated",
                    TestData.T01.password))) {
                assertNotNull(newTeacher);
                assertTrue(newTeacher.hasEntity());
                TeacherWithThesesResponse body = newTeacher.readEntity(TeacherWithThesesResponse.class);
                assertNotNull(body);
                assertNotNull(body.getId());
                tIds.add(body.getId());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                fail(e);
            }
        }
        assertEquals(numTeachers, tIds.size());

        pageParams.put("page", 0);
        pageParams.put("size", 2);
        try (Response response = request(SEARCH_TEACHER_PATH, TestData.T01, pageParams)
                .post(Entity.entity(SearchTeachersRequest.builder()
                                .institute(TestData.T02.institute)
                                .build(),
                        MediaType.APPLICATION_JSON_TYPE))) {
            assertNotNull(response);
            assertTrue(response.hasEntity());
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            assertTrue(response.getLength() > OBJECT_CONTENT_LENGTH);
            assertTrue(response.hasEntity());
            assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
            PagedResponse<TeacherWithThesesResponse> page = response.readEntity(new GenericType<PagedResponse<TeacherWithThesesResponse>>() {
            });
            assertNotNull(page);
            assertNotNull(page.getContent());
            assertNotNull(page.getPage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            fail(e);
        }
    }


}
