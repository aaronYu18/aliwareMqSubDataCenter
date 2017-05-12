package com.courier.db.dao;

import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.CRUDTemplate;
import com.courier.db.dao.crud.ExtSqlProp;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.Branch;
import com.courier.db.vModel.VBranch;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by aaron_yu on 15/5/27.
 */
public interface BranchMapper extends BaseMapper<Branch> {
    @SelectProvider(type = CRUDTemplate.class, method = "get")
    @Override
    public Branch get(Branch obj);

    @SelectProvider(type = CRUDTemplate.class, method = "findBy")
    @Override
    public List<Branch> findBy(Branch obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp, int pageNumber, int limit);

    @SelectProvider(type = CRUDTemplate.class, method = "findAll")
    @Override
    public List<Branch> findAll(Branch obj, List<SearchFilter> searchFilterList, ExtSqlProp sqlProp);


    public Branch findByOrgCode(@Param("orgCode")String orgCode);


    Branch getBranchByUId(@Param("userId")Long userId);

    Integer countVBranch(@Param("provinceCode") String provinceCode, @Param("orgCode") String orgCode);

    List<VBranch> findVBranchPage(@Param("provinceCode") String provinceCode, @Param("orgCode") String orgCode,
                                  @Param("index") Integer index, @Param("limit") Integer limit);

    VBranch sumVBranch(@Param("provinceCode") String provinceCode, @Param("orgCode") String orgCode,
                       @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    List<String> findByPCodeAndType(@Param("provinceCode") String province, @Param("type") Byte type, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    int countByPCodeAndType(@Param("provinceCode") String province, @Param("type") Byte type);

    List<String> findByBranchCodes(@Param("branchCodes") List<String> branchCodes,@Param("type")Byte type, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    int countByBranchCode(@Param("branchCode")String branchCode,@Param("type")Byte type);

    int countTerminalsByBranchCode(@Param("branchCode") String orgCode);

    List<String> findTerminalsByBranchCode(@Param("branchCode")String orgCode,  @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<String> findByBCodeType(@Param("branchCode")String branchCode, @Param("type")Byte type);

    List<String> findNotExistsBranchCodes();

    List<String> fetchCompanyByOrgCode(@Param("orgCode")String orgCode, @Param("type") Byte type, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    int countCompanyByOrgCode(@Param("orgCode")String orgCode, @Param("type") Byte type);

    List<String> fetchTerminalByOrgCode(@Param("orgCode")String orgCode);

}