using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public class UserProfile : BaseEntity, IUserProfile
    {

        public virtual string Name { get; set; }

        public virtual DateTime BirthDate { get; set; }

        public virtual IUser Owner { get; set; }

    }
}
