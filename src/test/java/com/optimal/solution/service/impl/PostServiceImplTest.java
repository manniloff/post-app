package com.optimal.solution.service.impl;

import com.optimal.solution.dto.CommentsDto;
import com.optimal.solution.dto.PostDto;
import com.optimal.solution.dto.PostsDto;
import com.optimal.solution.dto.UserDto;
import com.optimal.solution.model.Category;
import com.optimal.solution.model.Comment;
import com.optimal.solution.model.Post;
import com.optimal.solution.model.User;
import com.optimal.solution.repository.PostRepository;
import com.optimal.solution.repository.UserRepository;
import com.optimal.solution.service.PostService;
import com.optimal.solution.service.UserService;
import com.optimal.solution.util.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class PostServiceImplTest {

    @InjectMocks
    PostServiceImpl postServiceImpl;

    @Mock
    PostRepository postRepository;

    private Category category;

    private Comment comment = new Comment();

    private User user = new User();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        user.setLogin("Admin");

        category = new Category(1, "IT");
        comment.setId(1);
        comment.setText("Text");
        comment.setPostedDate(new Date(1245367422));
        comment.setUser(user);
    }

    @Test
    void findAll() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<PostsDto> posts = new ArrayList<>();
        List<Post> allPosts = new ArrayList<>();

        PostsDto postOne = new PostsDto(1,"Test","Test",new Date(1223121443),"admin", "IT", Collections.singleton(new CommentsDto()));
        PostsDto postTwo = new PostsDto(2,"Test2","Test2",new Date(1223647443),"user", "Business", Collections.singleton(new CommentsDto()));
        PostsDto postTree = new PostsDto(3,"Test3","Test3",new Date(1225631443),"test", "Politic", Collections.singleton(new CommentsDto()));

        posts.add(postOne);
        posts.add(postTwo);
        posts.add(postTree);

        Post post1 = new Post(1,"Test","Test",new Date(1223121443),new Date(1223121443), user, Collections.singleton(comment), Collections.singleton(category));
        Post post2 = new Post(1,"Test","Test",new Date(1223121443),new Date(1223121443), user, Collections.singleton(comment), Collections.singleton(category));
        Post post3 = new Post(1,"Test","Test",new Date(1223121443),new Date(1223121443), user, Collections.singleton(comment), Collections.singleton(category));

        allPosts.add(post1);
        allPosts.add(post2);
        allPosts.add(post3);

        when(postRepository.findAll()).thenAnswer(invocation -> allPosts);
        //when(PostService.class.getDeclaredMethod("getDataPosts").invoke(allPosts)).thenReturn(posts);

        List<PostsDto> usersDB = postServiceImpl.findAll();

        assertEquals(3, usersDB.size());
        verify(postRepository, times(1)).findAll();
    }


    @Test
    void findAccountAll() {
        List<PostsDto> posts = new ArrayList<>();
        List<Post> allPosts = new ArrayList<>();

        PostsDto postOne = new PostsDto(1,"Test","Test",new Date(1223121443),"admin", "IT", Collections.singleton(new CommentsDto()));
        PostsDto postTwo = new PostsDto(2,"Test2","Test2",new Date(1223647443),"user", "Business", Collections.singleton(new CommentsDto()));
        PostsDto postTree = new PostsDto(3,"Test3","Test3",new Date(1225631443),"test", "Politic", Collections.singleton(new CommentsDto()));

        posts.add(postOne);
        posts.add(postTwo);
        posts.add(postTree);

        Post post1 = new Post(1,"Test","Test",new Date(1223121443),new Date(1223121443), user, Collections.singleton(comment), Collections.singleton(category));
        Post post2 = new Post(1,"Test","Test",new Date(1223121443),new Date(1223121443), user, Collections.singleton(comment), Collections.singleton(category));
        Post post3 = new Post(1,"Test","Test",new Date(1223121443),new Date(1223121443), user, Collections.singleton(comment), Collections.singleton(category));

        allPosts.add(post1);
        allPosts.add(post2);
        allPosts.add(post3);

        when(postRepository.findAll()).thenAnswer(invocation -> allPosts);
        when(postRepository.findAllByUser(0)).thenReturn(allPosts);
        //when(PostService.class.getDeclaredMethod("getDataPosts").invoke(allPosts)).thenReturn(posts);

        List<PostsDto> usersDB = postServiceImpl.findAll();

        assertEquals(3, usersDB.size());
        verify(postRepository, times(1)).findAll();
        //verify(postRepository, times(1)).findAllByUser(0);
    }

    @Test
    void findById() {
        when(postRepository.findById(1))
                .thenAnswer(invocation -> Optional.of(new Post(1,"Test","Test",new Date(1223121443),new Date(1223121443), user, Collections.singleton(comment), Collections.singleton(category))));

        PostsDto post = postServiceImpl.findById(1);

        assertEquals("Test", post.getBody());
        assertEquals("Test", post.getTitle());
        assertEquals("Admin", post.getAuthor());
    }

    @Test
    void findByIdAccount() {
        when(postRepository.findById(1))
                .thenAnswer(invocation -> Optional.of(new Post(1,"Test","Test",new Date(1223121443),new Date(1223121443), user, Collections.singleton(comment), Collections.singleton(category))));
        when(postRepository.findByIdAndUser(1,0))
                .thenReturn(Optional.of(new Post(1,"Test","Test",new Date(1223121443),new Date(1223121443), user, Collections.singleton(comment), Collections.singleton(category))));

        PostsDto post = postServiceImpl.findById(1);

        assertEquals("Test", post.getBody());
        assertEquals("Test", post.getTitle());
        assertEquals("Admin", post.getAuthor());
    }

    /*@Test
    void createOrUpdate() {
        PostDto post = new PostDto();
                //new PostDto(1,"Test","Test",new Date(1223121443),null,1,Collections.singleton(1), Collections.singleton(1));
        post.setId(0);
        post.setTitle("Test");
        post.setBody("Test");
        post.setCreateDate(new Date(1223121443));
        post.setUpdateDate(null);

        when(postRepository.findByIdAndUser(0,0)).thenAnswer(invocation -> post);

        Post newPost = new Post();
                //new Post(1,"Test","Test",new Date(1223121443),new Date(1223121443), user, Collections.singleton(comment), Collections.singleton(category));
        newPost.setId(0);
        newPost.setTitle("Test");
        newPost.setBody("Test");
        newPost.setCreateDate(new Date(1223121443));
        newPost.setUpdateDate(null);
        newPost.setComments(Collections.singleton(new Comment()));
        try {
            postServiceImpl.createOrUpdate(post);
        } catch (NullPointerException e) {
            System.out.println(e);
        } finally {
            verify(postRepository, times(1)).save(newPost);
        }
    }*/

    @Test
    void deleteById() {
        Post post = new Post(1,"Test","Test",new Date(1223121443),new Date(1223121443), user, Collections.singleton(comment), Collections.singleton(category));

        when(postRepository.findById(0)).thenAnswer(invocation -> Optional.of(post));
        when(postRepository.findByIdAndUser(0,0)).thenAnswer(invocation -> Optional.of(post));

        try {
            postServiceImpl.deleteById(0);
        }catch (NullPointerException e){
            System.out.println(e);
        }finally {
            verify(postRepository, times(1)).deleteById(0);
        }
    }
}