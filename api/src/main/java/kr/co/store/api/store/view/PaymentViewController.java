package kr.co.store.api.store.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentViewController {

    @GetMapping("payment")
    public String paymentPage(Model model) {
        return "payment";
    }
}
