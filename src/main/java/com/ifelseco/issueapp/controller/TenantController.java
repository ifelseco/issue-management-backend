package com.ifelseco.issueapp.controller;


import com.ifelseco.issueapp.entity.Tenant;
import com.ifelseco.issueapp.entity.User;
import com.ifelseco.issueapp.model.BaseResponseModel;
import com.ifelseco.issueapp.model.ErrorModel;
import com.ifelseco.issueapp.model.TenantModel;
import com.ifelseco.issueapp.service.RoleService;
import com.ifelseco.issueapp.service.TenantService;
import com.ifelseco.issueapp.service.UserService;
import com.ifelseco.issueapp.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tenant")
@Secured("ROLE_ADMIN")
public class TenantController {

    private static final Logger LOG = LoggerFactory.getLogger(TenantController.class);

    private UserService userService;
    private TenantService tenantService;
    private RoleService roleService;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;

    public TenantController(UserService userService,
                            TenantService tenantService,
                            RoleService roleService,
                            ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userService = userService;
        this.tenantService = tenantService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity createTenant(@Valid @RequestBody TenantModel tenantModel,
                                       BindingResult errors,
                                       Principal principal
                                       ) {
        if (errors.hasErrors()) {
            List<ErrorModel> errorModelList =validationUtil.convertValidationErrors(errors);
            return new ResponseEntity(errorModelList, HttpStatus.BAD_REQUEST);
        } else {
            BaseResponseModel baseModel = new BaseResponseModel();
            try {
                User user = userService.findByUsername(principal.getName());
                if (user != null) {
                    return saveNewTenant(tenantModel, baseModel);
                } else {
                    baseModel.setResponseCode(1);
                    baseModel.setResponseMessage("Hata: Kullanıcı bulunamadı tekrar giriş yapınız.");
                    return new ResponseEntity(baseModel, HttpStatus.BAD_REQUEST);
                }

            } catch (Exception e) {
                LOG.error("Company Add Error : " + e.getClass() + " " + e.getMessage());
                baseModel.setResponseCode(1);
                baseModel.setResponseMessage("Error: Tenant Add.");
                return new ResponseEntity<>(baseModel, HttpStatus.BAD_REQUEST);
            }
        }





    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<BaseResponseModel> updateTenant(
            @RequestBody TenantModel tenantModel,
            Errors errors,
            Principal principal
            ) {
        BaseResponseModel baseModel = new BaseResponseModel();
        try {
            User user = userService.findByUsername(principal.getName());
            if (user != null) {
                if (errors.hasErrors()) {
                    return new ResponseEntity(validationUtil.convertValidationErrors(errors), HttpStatus.BAD_REQUEST);
                } else {
                    return updateExistingTenant(tenantModel, baseModel);
                }
            } else {
                baseModel.setResponseCode(1);
                baseModel.setResponseMessage("Hata: Kullanıcı bulunamadı tekrar giriş yapınız.");
                return new ResponseEntity<>(baseModel, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            LOG.error("Company Update Error : " + e.getClass() + " " + e.getMessage());
            baseModel.setResponseCode(1);
            baseModel.setResponseMessage("Error: Tenant Add.");
            return new ResponseEntity<>(baseModel, HttpStatus.BAD_REQUEST);
        }


    }

    private ResponseEntity<BaseResponseModel> updateExistingTenant(@RequestBody TenantModel tenantModel, BaseResponseModel baseModel) {
        Tenant tenant = tenantService.findOne(tenantModel.getId());
        if (tenant != null) {
            tenant = modelMapper.map(tenantModel, Tenant.class);
            tenantService.createTenant(tenant);
            baseModel.setResponseMessage("Firma Güncelleme İşlemi Başarılı");
            return new ResponseEntity<>(baseModel, HttpStatus.OK);
        } else {
            baseModel.setResponseCode(1);
            baseModel.setResponseMessage("Hata: Verilen id'de firma bulunamadı.");
            return new ResponseEntity<>(baseModel, HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<BaseResponseModel> saveNewTenant(@RequestBody TenantModel tenantModel, BaseResponseModel baseModel) {
        tenantService.createTenant(modelMapper.map(tenantModel, Tenant.class));
        baseModel.setResponseMessage("Firma Ekleme İşlemi Başarılı");
        return new ResponseEntity<>(baseModel, HttpStatus.OK);
    }




}
