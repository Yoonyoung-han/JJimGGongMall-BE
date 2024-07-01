package hanghae99.JJimGGongMall.service;

import hanghae99.JJimGGongMall.domain.Like;
import hanghae99.JJimGGongMall.domain.Product;
import hanghae99.JJimGGongMall.domain.User;
import hanghae99.JJimGGongMall.dto.ProductDto;
import hanghae99.JJimGGongMall.dto.request.RequestWishlistDto;
import hanghae99.JJimGGongMall.repository.interfaces.LikeRepository;
import hanghae99.JJimGGongMall.repository.interfaces.ProductRepository;
import hanghae99.JJimGGongMall.repository.interfaces.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public String addWishlist(Long userId, RequestWishlistDto request) {
        Like wishlist = Like.builder()
                .connectId(request.getProductId())
                .likeType("wishlist")
                .build();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found : " + userId));

        wishlist.setUser(user);

        likeRepository.save(wishlist);
        return "Product added to wishlist";
    }

    public List<ProductDto> getWishlist(Long userId) {
        List<Like> wishlist = likeRepository.findAllByUserIdAndLikeType(userId,"wishlist");
        // 1. wishlist에서 connectId를 추출하여 리스트로 만듭니다.
        List<Long> connectIds = wishlist.stream()
                .map(Like::getConnectId)
                .toList();

        // 2. ProductRepository를 사용하여 connectIds에 해당하는 Product 엔티티들을 찾습니다.
        List<Product> wishProducts = productRepository.findAllByIdIn(connectIds);

        return wishProducts.stream()
                .map(ProductDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public String removeWishlist(Long userId, RequestWishlistDto request) {
        Long target = request.getProductId();
        likeRepository.deleteByUserIdAndConnectId(userId,target);

        return "Item removed from wishlist successfully.";
    }
}
